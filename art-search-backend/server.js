const express = require('express');
const cors = require('cors');
const fs = require('fs');
const path = require('path');
const jwt = require('jsonwebtoken');
const { createClient } = require('@supabase/supabase-js');
const { ART_HISTORY_REFERENCE } = require('./data/artHistoryReference');

const app = express();
const PORT = process.env.PORT || 3000;
const NAVER_CLIENT_ID = process.env.NAVER_CLIENT_ID;
const NAVER_CLIENT_SECRET = process.env.NAVER_CLIENT_SECRET;
const ALLOWED_ORIGIN = process.env.ALLOWED_ORIGIN || '*';
const GEMINI_API_KEY = process.env.GEMINI_API_KEY;
const GEMINI_MODEL = process.env.GEMINI_MODEL || 'gemini-flash-latest';
const GOOGLE_CLIENT_ID = process.env.GOOGLE_CLIENT_ID;
const DAILY_CURATOR_LIMIT = Number(process.env.DAILY_CURATOR_LIMIT || 20);
const SUPABASE_URL = process.env.SUPABASE_URL;
const SUPABASE_SERVICE_KEY = process.env.SUPABASE_SERVICE_KEY;

// 백엔드 자체 세션 토큰(로그인 방식은 그대로 Google, 매 요청마다 Google에 재검증하지 않기 위함)
const JWT_SECRET = process.env.JWT_SECRET || null;
// Google Drive 백업용 (Supabase 데이터를 주기적으로 내보내는 안전망)
const GOOGLE_CLIENT_SECRET = process.env.GOOGLE_CLIENT_SECRET || null;
const GOOGLE_REFRESH_TOKEN = process.env.GOOGLE_REFRESH_TOKEN || null;
// 오늘의 지역 날씨 표시용
const OPENWEATHER_API_KEY = process.env.OPENWEATHER_API_KEY || null;
// 영구 디스크 경로 (Render 등, 없으면 서버 재시작 시 초기화되는 메모리 저장만 씀)
const DATA_DIR = process.env.DATA_DIR || null;
if (DATA_DIR) fs.mkdirSync(DATA_DIR, { recursive: true });

const supabase = (SUPABASE_URL && SUPABASE_SERVICE_KEY)
  ? createClient(SUPABASE_URL, SUPABASE_SERVICE_KEY)
  : null;

app.use(cors({ origin: ALLOWED_ORIGIN }));
app.use(express.json({ limit: '200kb' }));

// ---------- Google 로그인 확인 ----------
async function verifyGoogleToken(idToken) {
  if (!idToken) return null;
  try {
    const r = await fetch('https://oauth2.googleapis.com/tokeninfo?id_token=' + encodeURIComponent(idToken));
    if (!r.ok) return null;
    const info = await r.json();
    if (!GOOGLE_CLIENT_ID || info.aud !== GOOGLE_CLIENT_ID) return null;
    if (info.email_verified !== 'true' && info.email_verified !== true) return null;
    return { email: info.email, name: info.name || info.email, picture: info.picture || '' };
  } catch (e) {
    return null;
  }
}

async function requireAuth(req, res, next) {
  const header = req.get('authorization') || '';
  const token = header.startsWith('Bearer ') ? header.slice(7) : null;
  if (!token) {
    return res.status(401).json({ error: '로그인이 필요해요.' });
  }
  // 먼저 우리 백엔드가 발급한 자체 세션 토큰인지 확인 (구글에 매 요청마다 재검증하지 않기 위함).
  // Google이 서명한 idToken은 JWT_SECRET으로 검증되지 않으므로 실패하면 아래 idToken 검증으로 자연히 폴백됨.
  if (JWT_SECRET) {
    try {
      const payload = jwt.verify(token, JWT_SECRET);
      req.user = { email: payload.email, name: payload.name, picture: payload.picture };
      return next();
    } catch (e) {
      // 우리 토큰이 아니거나 만료됨 -> 아래에서 Google idToken으로 취급해 재검증
    }
  }
  const user = await verifyGoogleToken(token);
  if (!user) {
    return res.status(401).json({ error: '로그인이 필요해요.' });
  }
  req.user = user;
  next();
}

// POST /api/auth/session  { Authorization: Bearer <Google idToken> } -> { token, user }
// Google idToken을 한 번 검증하고, 이후 요청에 쓸 백엔드 자체 세션 토큰을 발급해줌
app.post('/api/auth/session', async (req, res) => {
  if (!JWT_SECRET) {
    return res.status(503).json({ error: '세션 기능이 아직 설정되어 있지 않아요.' });
  }
  const header = req.get('authorization') || '';
  const idToken = header.startsWith('Bearer ') ? header.slice(7) : null;
  const user = await verifyGoogleToken(idToken);
  if (!user) {
    return res.status(401).json({ error: '로그인이 필요해요.' });
  }
  const token = jwt.sign(user, JWT_SECRET, { expiresIn: '7d' });
  res.json({ token, user });
});

// ---------- 하루 사용량 제한 (DATA_DIR 있으면 파일로 영속화, 없으면 메모리 전용이라 재시작 시 초기화) ----------
var USAGE_FILE = DATA_DIR ? path.join(DATA_DIR, 'usage.json') : null;
var usageByEmail = new Map();
(function loadUsageFromDisk() {
  if (!USAGE_FILE) return;
  try {
    var saved = JSON.parse(fs.readFileSync(USAGE_FILE, 'utf8'));
    Object.keys(saved).forEach(function (email) { usageByEmail.set(email, saved[email]); });
  } catch (e) {
    // 파일이 없거나 손상됨 -> 빈 상태로 시작
  }
})();
function checkAndBumpUsage(email) {
  var today = new Date().toISOString().slice(0, 10);
  var entry = usageByEmail.get(email);
  if (!entry || entry.day !== today) {
    entry = { day: today, count: 0 };
  }
  entry.count += 1;
  usageByEmail.set(email, entry);
  if (USAGE_FILE) {
    try {
      fs.writeFileSync(USAGE_FILE, JSON.stringify(Object.fromEntries(usageByEmail)));
    } catch (e) {
      // 저장 실패해도 이번 요청 자체는 그대로 진행
    }
  }
  return entry.count <= DAILY_CURATOR_LIMIT;
}

// ---------- 큐레이터용 경량 검색(retrieval) ----------
// 전시 목록 전체를 매번 프롬프트에 넣는 대신, 질문과 겹치는 단어가 많은 전시부터 상위 N개만 골라서 넣는다.
// (벡터DB 없이도 제목/장소/지역/장르/분위기 키워드 매칭만으로 충분히 관련도를 가릴 수 있는 규모라 이 방식을 씀)
function scoreExhibition(ex, queryTokens) {
  var haystack = [ex.title, ex.venue, ex.region, ex.genre, ex.mood, ex.status]
    .join(' ')
    .toLowerCase();
  var score = 0;
  queryTokens.forEach(function (t) {
    if (haystack.indexOf(t) >= 0) score += 1;
  });
  return score;
}

function retrieveRelevantExhibitions(query, list, topK) {
  var tokens = String(query || '')
    .toLowerCase()
    .split(/[^\p{L}\p{N}]+/u)
    .filter(function (t) { return t.length >= 2; });

  if (!tokens.length || list.length <= topK) return list.slice(0, topK);

  var scored = list.map(function (ex) {
    return { ex: ex, score: scoreExhibition(ex, tokens) };
  });
  var anyMatch = scored.some(function (s) { return s.score > 0; });
  if (!anyMatch) return list.slice(0, topK);

  scored.sort(function (a, b) { return b.score - a.score; });
  return scored.slice(0, topK).map(function (s) { return s.ex; });
}

function stripTags(s) {
  return String(s || '')
    .replace(/<\/?b>/g, '')
    .replace(/&quot;/g, '"')
    .replace(/&amp;/g, '&')
    .replace(/&lt;/g, '<')
    .replace(/&gt;/g, '>');
}

app.get('/health', (req, res) => {
  res.json({ ok: true });
});

// GET /api/search?q=검색어&type=blog|webkr|news  (기본 blog)
app.get('/api/search', async (req, res) => {
  const q = (req.query.q || '').trim();
  const type = ['blog', 'webkr', 'news'].includes(req.query.type) ? req.query.type : 'blog';

  if (!q) {
    return res.status(400).json({ error: 'q(검색어) 파라미터가 필요해요.' });
  }
  if (!NAVER_CLIENT_ID || !NAVER_CLIENT_SECRET) {
    return res.status(500).json({ error: '서버에 네이버 API 키가 설정되어 있지 않아요. NAVER_CLIENT_ID / NAVER_CLIENT_SECRET 환경변수를 확인해주세요.' });
  }

  const url = `https://openapi.naver.com/v1/search/${type}.json?query=${encodeURIComponent(q)}&display=5&sort=sim`;

  try {
    const naverRes = await fetch(url, {
      headers: {
        'X-Naver-Client-Id': NAVER_CLIENT_ID,
        'X-Naver-Client-Secret': NAVER_CLIENT_SECRET
      }
    });

    if (!naverRes.ok) {
      const text = await naverRes.text();
      return res.status(naverRes.status).json({ error: '네이버 검색 API 호출에 실패했어요.', detail: text });
    }

    const data = await naverRes.json();
    const items = (data.items || []).map((item) => ({
      title: stripTags(item.title),
      description: stripTags(item.description),
      link: item.link
    }));

    res.json({ query: q, items });
  } catch (err) {
    res.status(500).json({ error: '검색 중 오류가 발생했어요.', detail: String(err) });
  }
});

// ---------- 큐레이터 질문 분류 ----------
// 예전에는 모든 질문에 규칙+예시+미술사자료+전시목록을 통째로 넣은 하나의 긴 프롬프트를 썼다.
// 이 때문에 (1) 프롬프트가 길어져 503(모델 과부하)이 잦고 (2) 응답이 느리고
// (3) AI가 프롬프트 속 지시문("Output Format" 등)을 그대로 따라 출력하는 문제가 있었다.
// 질문을 4가지 유형으로 나눠서 유형별로 꼭 필요한 데이터만 프롬프트에 넣는 방식으로 이를 줄인다.
// (분류 자체에 별도로 AI를 호출하면 지연시간이 늘고 목적에도 안 맞으므로, 키워드 기반의
//  간단한 규칙 분류만 사용한다.)
var EXHIBITION_KEYWORDS = ['전시', '추천', '가볼', '볼만한', '관람', '전시회', '전시장', '어디서', '어디에',
  '서울', '경기', '인천', '대구', '대전', '광주', '전북', '전남', '울산', '제주', '부산', '강원', '경남', '경북', '충남', '충북', '세종'];
// 시대/사조 이름은 "화가" 같은 단어와 함께 나와도 history로 분류되어야 하므로 artist보다 먼저 검사한다.
var HISTORY_KEYWORDS = ['사조', '시대', '유파', '운동', '역사', '언제부터', '몇 세기', '몇세기',
  '르네상스', '바로크', '로코코', '매너리즘', '고딕', '인상주의', '입체주의', '초현실주의', '야수파',
  '표현주의', '다다이즘', '미래주의', '아르누보', '팝아트', '팝 아트', '미니멀리즘', '포스트모더니즘',
  '신고전주의', '낭만주의', '리얼리즘', '상징주의', '아방가르드', '모더니즘'];
var ARTWORK_KEYWORDS = ['이 그림', '이 작품', '그림 설명', '작품 설명', '이미지 속', '사진 속', '업로드한'];
var ARTIST_KEYWORDS = ['화가', '작가', '예술가', '화백'];

function containsAny(text, words) {
  return words.some(function (w) { return text.indexOf(w) >= 0; });
}

// 질문을 artwork / history / artist / exhibition 중 하나로 분류한다.
// 검사 순서: artwork(가장 구체적) -> history(사조/시대 키워드) -> artist(일반 "화가/작가" 언급)
// -> exhibition. 애매하면 사이트 본연의 기능인 exhibition으로 취급한다.
function classifyQuery(query) {
  var q = String(query || '');
  if (containsAny(q, ARTWORK_KEYWORDS)) return 'artwork';
  if (containsAny(q, HISTORY_KEYWORDS)) return 'history';
  if (containsAny(q, ARTIST_KEYWORDS)) return 'artist';
  return 'exhibition';
}

// ---------- 유형별 프롬프트 생성 ----------
// 공통 지시문은 한 줄로 짧게 유지한다. 예시 답안이나 "Output Format" 같은 형식 설명을 프롬프트에
// 넣으면 AI가 그 문구 자체를 따라 출력해버리는 문제가 있었으므로 최소한으로만 남긴다.
var COMMON_STYLE = '친절하고 따뜻한 존댓말로, 자연스러운 한국어 문장 2~4개로 바로 답해주세요. 분석 과정이나 형식 설명 없이 답변 내용만 출력하세요.';

function buildHistoryPrompt(query, reminder) {
  return '당신은 미술 전문 큐레이터입니다. 아래 미술사 자료를 참고해서 사용자 질문에 답해주세요.\n\n' +
    '[미술사 참고자료]\n' + ART_HISTORY_REFERENCE +
    '\n\n[질문]\n' + query +
    '\n\n' + COMMON_STYLE + (reminder ? '\n' + reminder : '');
}

function buildArtistPrompt(query, reminder) {
  return '당신은 미술 전문 큐레이터입니다. 화가에 대해 알고 있는 지식과 아래 미술사 자료를 참고해서 질문에 답해주세요.\n\n' +
    '[미술사 참고자료]\n' + ART_HISTORY_REFERENCE +
    '\n\n[질문]\n' + query +
    '\n\n' + COMMON_STYLE + (reminder ? '\n' + reminder : '');
}

// artwork(작품 설명): 전시 목록/미술사 자료 대신 요청에 실려온 작품 정보(또는 이미지 설명)만 사용한다.
// 프런트가 아직 작품 정보를 보내지 않는 경우도 있으므로, 없으면 그 사실을 프롬프트에 명시해
// AI가 근거 없는 내용을 지어내지 않도록 한다.
function buildArtworkPrompt(query, artwork, reminder) {
  var artworkInfo = artwork ? String(artwork) : '제공된 작품 정보가 없습니다. 질문에 나온 내용만으로 답하세요.';
  return '당신은 미술 전문 큐레이터입니다. 아래 작품 정보를 참고해서 질문에 답해주세요.\n\n' +
    '[작품 정보]\n' + artworkInfo +
    '\n\n[질문]\n' + query +
    '\n\n' + COMMON_STYLE + (reminder ? '\n' + reminder : '');
}

function buildExhibitionPrompt(query, context, historyLine, reminder) {
  return '당신은 "미술이 있는 날들" 사이트의 큐레이터입니다. 아래 [전시 목록]에 있는 전시만 추천하고, ' +
    '목록에 없는 전시는 지어내지 마세요. 목록에 없으면 없다고 짧게 답하세요.\n\n' +
    '[전시 목록]\n' + context + historyLine +
    '\n\n[질문]\n' + query +
    '\n\n' + COMMON_STYLE + (reminder ? '\n' + reminder : '');
}

// 답변에 등장한 전시 제목을 찾아 그 전시의 id를 추천 id로 간주한다.
// 예전에는 AI에게 "답변 마지막 줄에 IDS: id1,id2 를 적어라"라고 시켰는데, 이 형식 지시문
// 자체가 답변에 새어나오는 원인 중 하나였다. AI에게 형식을 기억시키는 대신 서버가 직접 매칭한다.
function extractRecommendedIds(answer, list) {
  if (!answer || !list.length) return [];
  return list
    .filter(function (ex) { return ex.title && answer.indexOf(ex.title) >= 0; })
    .map(function (ex) { return ex.id; });
}

// ---------- Gemini 호출 ----------
var GEMINI_RETRY_DELAYS_MS = [1000, 2000, 4000]; // 503(과부하) 시 1초 -> 2초 -> 4초 순으로 최대 3회 재시도

function sleep(ms) {
  return new Promise(function (resolve) { setTimeout(resolve, ms); });
}

function fetchGemini(prompt) {
  return fetch(
    'https://generativelanguage.googleapis.com/v1beta/models/' + GEMINI_MODEL + ':generateContent?key=' + GEMINI_API_KEY,
    {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        contents: [{ parts: [{ text: prompt }] }],
        generationConfig: { temperature: 0.4, maxOutputTokens: 800, thinkingConfig: { thinkingBudget: 0 } }
      })
    }
  );
}

// 구글 쪽 모델 일시 과부하(503 UNAVAILABLE)에 대비해 지수 백오프로 재시도한다.
// (기존에는 600ms 뒤 1회만 재시도했는데, 그정도로는 부족해 503이 그대로 사용자에게 노출되는 일이 잦았다.)
async function callGeminiWithRetry(prompt) {
  var res = await fetchGemini(prompt);
  for (var i = 0; res.status === 503 && i < GEMINI_RETRY_DELAYS_MS.length; i++) {
    await sleep(GEMINI_RETRY_DELAYS_MS[i]);
    res = await fetchGemini(prompt);
  }
  return res;
}

// 프롬프트 속 지시문("Output Format", "Rules:", "분석 과정" 등)이 답변에 그대로 새어나오면
// 깨진 답으로 간주해 재요청한다.
function looksBroken(answer) {
  if (!answer) return true;
  if (/output format|format\s*:|must end with|example\s*:|rules\s*:|분석 과정|\*\*/i.test(answer)) return true;
  if (answer.length < 6) return true;
  if (!/[.!?요다임음함죠]["')\]]?$/.test(answer)) return true;
  return false;
}

function extractAnswer(data) {
  var candidate = (data.candidates && data.candidates[0]) || null;
  var finishReason = candidate && candidate.finishReason;
  var parts = (candidate && candidate.content && candidate.content.parts) || [];
  var text = parts.filter(function (p) { return !p.thought; }).map(function (p) { return p.text || ''; }).join('').trim();
  return { finishReason: finishReason || null, text: text, partsCount: parts.length };
}

// buildPromptFn(reminder) 형태의 프롬프트 빌더를 받아 Gemini에 질의하고,
// 답변이 깨진 것처럼 보이면 형식 리마인더를 붙여 한 번만 더 시도한다.
// 503 등 호출 자체 실패는 예외로 던져서 라우트 핸들러가 HTTP 에러 응답을 만들도록 한다.
async function askCuratorGemini(buildPromptFn) {
  var geminiRes = await callGeminiWithRetry(buildPromptFn());
  if (!geminiRes.ok) {
    var errText = await geminiRes.text();
    console.error('Gemini error', geminiRes.status, errText);
    var err = new Error('gemini_failed');
    err.status = geminiRes.status;
    throw err;
  }

  var data = await geminiRes.json();
  var result = extractAnswer(data);
  if (result.finishReason && result.finishReason !== 'STOP') {
    console.error('Gemini finished abnormally', result.finishReason);
    return {
      answer: '죄송해요, 답변 분량이 초과되어 내용을 정리하지 못했어요. 조금 더 짧게 다시 물어봐주실래요?',
      finishReason: result.finishReason, text: result.text, partsCount: result.partsCount, retried: false
    };
  }

  var retried = false;
  if (looksBroken(result.text)) {
    retried = true;
    var reminder = '(형식 설명이나 마크다운("**") 없이, 완성된 한국어 문장 2~4개만 출력하세요.)';
    var retryRes = await callGeminiWithRetry(buildPromptFn(reminder));
    if (retryRes.ok) {
      var retryData = await retryRes.json();
      var retryResult = extractAnswer(retryData);
      if (!retryResult.finishReason || retryResult.finishReason === 'STOP') {
        result = retryResult;
      }
    }
  }

  return {
    answer: (!looksBroken(result.text) && result.text) || '음, 지금은 답을 정리하지 못했어요. 다시 물어봐주실래요?',
    finishReason: result.finishReason,
    text: result.text,
    partsCount: result.partsCount,
    retried: retried
  };
}

// 질문 유형에 따라 이번 요청에 실제로 필요한 데이터(전시 목록 vs 미술사 자료 vs 작품 정보)만 준비하고,
// 그에 맞는 프롬프트 빌더를 고른다. 전시 목록 조회/직렬화는 exhibition 질문일 때만 수행한다.
function prepareCuratorRequest(category, query, exhibitions, history, artwork) {
  if (category === 'exhibition') {
    var fullList = Array.isArray(exhibitions) ? exhibitions.slice(0, 200) : [];
    var list = retrieveRelevantExhibitions(query, fullList, 24);
    var context = list.map(function (ex) {
      return '- [' + ex.id + '] ' + ex.title + ' · ' + ex.venue + ' (' + ex.region + ') · ' + ex.period +
        ' · ' + ex.genre + ' · ' + (ex.status || '') + ' · 분위기: ' + (ex.mood || '');
    }).join('\n');
    var historyList = Array.isArray(history) ? history.slice(0, 20) : [];
    var historyLine = historyList.length
      ? '\n\n[이 사용자가 이전에 즐겨찾기하거나 본 전시]\n' + historyList.join(', ') + '\n이 취향을 참고해서 비슷한 결의 전시를 우선 추천해도 좋아요.'
      : '';
    return {
      list: list,
      totalCount: fullList.length,
      buildPromptFn: function (reminder) { return buildExhibitionPrompt(query, context, historyLine, reminder); }
    };
  }
  if (category === 'history') {
    return { list: [], totalCount: 0, buildPromptFn: function (reminder) { return buildHistoryPrompt(query, reminder); } };
  }
  if (category === 'artist') {
    return { list: [], totalCount: 0, buildPromptFn: function (reminder) { return buildArtistPrompt(query, reminder); } };
  }
  return { list: [], totalCount: 0, buildPromptFn: function (reminder) { return buildArtworkPrompt(query, artwork, reminder); } };
}

// POST /api/curator  { query, exhibitions: [...], history: [...], artwork? }  (로그인 필요)
// artwork는 선택 필드로, 작품에 대한 텍스트 설명(또는 향후 이미지 설명)을 담는다.
app.post('/api/curator', requireAuth, async (req, res) => {
  if (!GEMINI_API_KEY) {
    return res.status(503).json({ error: 'AI 큐레이터가 아직 설정되어 있지 않아요. GEMINI_API_KEY를 확인해주세요.' });
  }
  if (!checkAndBumpUsage(req.user.email)) {
    return res.status(429).json({ error: '오늘 AI 큐레이터 사용 한도를 다 쓰셨어요. 내일 다시 이용해주세요.' });
  }

  const { query, exhibitions, history, artwork } = req.body || {};
  if (!query || typeof query !== 'string') {
    return res.status(400).json({ error: 'query가 필요해요.' });
  }

  const category = classifyQuery(query);
  const { list, totalCount, buildPromptFn } = prepareCuratorRequest(category, query, exhibitions, history, artwork);

  try {
    const result = await askCuratorGemini(buildPromptFn);
    const ids = category === 'exhibition' ? extractRecommendedIds(result.answer, list) : [];

    res.json({
      answer: result.answer,
      ids: ids,
      debug: {
        category: category,
        finishReason: result.finishReason,
        rawTextLength: result.text.length,
        partsCount: result.partsCount,
        retrievedCount: list.length,
        totalCount: totalCount,
        retried: result.retried
      }
    });
  } catch (err) {
    if (err && err.status) {
      const msg = err.status === 503
        ? 'AI가 지금 많이 몰려서 답을 못 가져왔어요. 잠시 후 다시 시도해주실래요?'
        : 'AI 응답을 가져오지 못했어요.';
      return res.status(502).json({ error: msg });
    }
    console.error(err);
    res.status(500).json({ error: 'AI 응답을 가져오는 중 오류가 발생했어요.' });
  }
});

function requireSupabase(req, res, next) {
  if (!supabase) {
    return res.status(503).json({ error: '즐겨찾기 기능이 아직 설정되어 있지 않아요.' });
  }
  next();
}

// GET /api/favorites  (로그인 필요) -> { ids: [...] }
app.get('/api/favorites', requireAuth, requireSupabase, async (req, res) => {
  const { data, error } = await supabase
    .from('user_activity')
    .select('exhibit_id')
    .eq('email', req.user.email)
    .eq('type', 'favorite');
  if (error) return res.status(500).json({ error: '즐겨찾기를 불러오지 못했어요.' });
  res.json({ ids: data.map(function (row) { return row.exhibit_id; }) });
});

// POST /api/favorites  { exhibitId, on }  (로그인 필요)
app.post('/api/favorites', requireAuth, requireSupabase, async (req, res) => {
  const { exhibitId, on } = req.body || {};
  if (!exhibitId) return res.status(400).json({ error: 'exhibitId가 필요해요.' });

  if (on) {
    const { error } = await supabase
      .from('user_activity')
      .upsert({ email: req.user.email, exhibit_id: exhibitId, type: 'favorite' }, { onConflict: 'email,exhibit_id,type' });
    if (error) return res.status(500).json({ error: '즐겨찾기 저장에 실패했어요.' });
  } else {
    const { error } = await supabase
      .from('user_activity')
      .delete()
      .eq('email', req.user.email)
      .eq('exhibit_id', exhibitId)
      .eq('type', 'favorite');
    if (error) return res.status(500).json({ error: '즐겨찾기 해제에 실패했어요.' });
  }
  res.json({ ok: true });
});

// GET /api/visits  (로그인 필요) -> { ids: [...] }
app.get('/api/visits', requireAuth, requireSupabase, async (req, res) => {
  const { data, error } = await supabase
    .from('user_activity')
    .select('exhibit_id')
    .eq('email', req.user.email)
    .eq('type', 'visit');
  if (error) return res.status(500).json({ error: '방문기록을 불러오지 못했어요.' });
  res.json({ ids: data.map(function (row) { return row.exhibit_id; }) });
});

// POST /api/visits  { exhibitId }  (로그인 필요) - 전시 상세를 열어볼 때마다 기록
app.post('/api/visits', requireAuth, requireSupabase, async (req, res) => {
  const { exhibitId } = req.body || {};
  if (!exhibitId) return res.status(400).json({ error: 'exhibitId가 필요해요.' });
  const { error } = await supabase
    .from('user_activity')
    .upsert(
      { email: req.user.email, exhibit_id: exhibitId, type: 'visit', created_at: new Date().toISOString() },
      { onConflict: 'email,exhibit_id,type' }
    );
  if (error) return res.status(500).json({ error: '방문기록 저장에 실패했어요.' });
  res.json({ ok: true });
});

// ---------- 오늘의 지역 날씨 ----------
var REGION_COORDS = {
  '서울': [37.5665, 126.9780], '경기': [37.4138, 127.5183], '인천': [37.4563, 126.7052],
  '대구': [35.8714, 128.6014], '대전': [36.3504, 127.3845], '광주': [35.1595, 126.8526],
  '전북': [35.7175, 127.1530], '울산': [35.5384, 129.3114], '제주': [33.4996, 126.5312],
  '부산': [35.1796, 129.0756], '강원': [37.8228, 128.1555], '경남': [35.4606, 128.2132],
  '경북': [36.4919, 128.8889], '충남': [36.5184, 126.8000], '충북': [36.6357, 127.4917],
  '세종': [36.4800, 127.2890]
};

// GET /api/weather?region=서울  -> { region, description, temp }
app.get('/api/weather', async (req, res) => {
  if (!OPENWEATHER_API_KEY) {
    return res.status(503).json({ error: '날씨 기능이 아직 설정되어 있지 않아요.' });
  }
  var region = REGION_COORDS[req.query.region] ? req.query.region : '서울';
  var coords = REGION_COORDS[region];
  try {
    var url = 'https://api.openweathermap.org/data/2.5/weather?lat=' + coords[0] + '&lon=' + coords[1] +
      '&appid=' + OPENWEATHER_API_KEY + '&units=metric&lang=kr';
    var weatherRes = await fetch(url);
    if (!weatherRes.ok) {
      return res.status(502).json({ error: '날씨 정보를 가져오지 못했어요.' });
    }
    var data = await weatherRes.json();
    var description = (data.weather && data.weather[0] && data.weather[0].description) || null;
    var temp = (data.main && typeof data.main.temp === 'number') ? Math.round(data.main.temp) : null;
    res.json({ region: region, description: description, temp: temp });
  } catch (err) {
    res.status(500).json({ error: '날씨 정보를 가져오는 중 오류가 발생했어요.' });
  }
});

// ---------- Supabase -> Google Drive 주기적 백업 (Supabase 자체도 안전하지만, 추가 안전망) ----------
async function getDriveAccessToken() {
  if (!GOOGLE_CLIENT_ID || !GOOGLE_CLIENT_SECRET || !GOOGLE_REFRESH_TOKEN) return null;
  try {
    var r = await fetch('https://oauth2.googleapis.com/token', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({
        client_id: GOOGLE_CLIENT_ID,
        client_secret: GOOGLE_CLIENT_SECRET,
        refresh_token: GOOGLE_REFRESH_TOKEN,
        grant_type: 'refresh_token'
      })
    });
    if (!r.ok) return null;
    var data = await r.json();
    return data.access_token || null;
  } catch (e) {
    return null;
  }
}

var driveBackupFolderId = null;
async function ensureDriveBackupFolder(accessToken) {
  if (driveBackupFolderId) return driveBackupFolderId;
  var folderName = '미술이 있는 날들 백업';
  var q = "name='" + folderName + "' and mimeType='application/vnd.google-apps.folder' and trashed=false";
  var listRes = await fetch('https://www.googleapis.com/drive/v3/files?q=' + encodeURIComponent(q) + '&fields=files(id,name)', {
    headers: { Authorization: 'Bearer ' + accessToken }
  });
  var listData = await listRes.json();
  var folderId = listData.files && listData.files[0] && listData.files[0].id;
  if (!folderId) {
    var createRes = await fetch('https://www.googleapis.com/drive/v3/files', {
      method: 'POST',
      headers: { Authorization: 'Bearer ' + accessToken, 'Content-Type': 'application/json' },
      body: JSON.stringify({ name: folderName, mimeType: 'application/vnd.google-apps.folder' })
    });
    var createData = await createRes.json();
    folderId = createData.id;
  }
  driveBackupFolderId = folderId;
  return folderId;
}

async function uploadJsonToDrive(accessToken, folderId, filename, jsonText) {
  var q = "name='" + filename + "' and '" + folderId + "' in parents and trashed=false";
  var listRes = await fetch('https://www.googleapis.com/drive/v3/files?q=' + encodeURIComponent(q) + '&fields=files(id)', {
    headers: { Authorization: 'Bearer ' + accessToken }
  });
  var listData = await listRes.json();
  var existingId = listData.files && listData.files[0] && listData.files[0].id;

  var boundary = 'artdays-backup-boundary';
  var metadata = existingId ? {} : { name: filename, parents: [folderId] };
  var body = '--' + boundary + '\r\n' +
    'Content-Type: application/json; charset=UTF-8\r\n\r\n' + JSON.stringify(metadata) + '\r\n' +
    '--' + boundary + '\r\n' +
    'Content-Type: application/json\r\n\r\n' + jsonText + '\r\n' +
    '--' + boundary + '--';

  var uploadUrl = existingId
    ? 'https://www.googleapis.com/upload/drive/v3/files/' + existingId + '?uploadType=multipart'
    : 'https://www.googleapis.com/upload/drive/v3/files?uploadType=multipart';
  await fetch(uploadUrl, {
    method: existingId ? 'PATCH' : 'POST',
    headers: { Authorization: 'Bearer ' + accessToken, 'Content-Type': 'multipart/related; boundary=' + boundary },
    body: body
  });
}

async function runSupabaseDriveBackup() {
  if (!supabase || !GOOGLE_CLIENT_ID || !GOOGLE_CLIENT_SECRET || !GOOGLE_REFRESH_TOKEN) return;
  try {
    var accessToken = await getDriveAccessToken();
    if (!accessToken) return;
    var { data, error } = await supabase.from('user_activity').select('*');
    if (error) {
      console.warn('Drive 백업용 Supabase 조회 실패', error);
      return;
    }
    var folderId = await ensureDriveBackupFolder(accessToken);
    await uploadJsonToDrive(accessToken, folderId, 'user_activity_backup.json', JSON.stringify(data, null, 2));
    console.log('Supabase 데이터 Google Drive 백업 완료 (' + data.length + '건)');
  } catch (e) {
    console.warn('Google Drive 백업 실패', e);
  }
}
if (supabase && GOOGLE_CLIENT_ID && GOOGLE_CLIENT_SECRET && GOOGLE_REFRESH_TOKEN) {
  runSupabaseDriveBackup();
  setInterval(runSupabaseDriveBackup, 6 * 60 * 60 * 1000);
}

app.listen(PORT, () => {
  console.log(`art-search-backend listening on port ${PORT}`);
});

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

// POST /api/curator  { query, exhibitions: [...], history: [...] }  (로그인 필요)
app.post('/api/curator', requireAuth, async (req, res) => {
  if (!GEMINI_API_KEY) {
    return res.status(503).json({ error: 'AI 큐레이터가 아직 설정되어 있지 않아요. GEMINI_API_KEY를 확인해주세요.' });
  }
  if (!checkAndBumpUsage(req.user.email)) {
    return res.status(429).json({ error: '오늘 AI 큐레이터 사용 한도를 다 쓰셨어요. 내일 다시 이용해주세요.' });
  }

  const { query, exhibitions, history } = req.body || {};
  if (!query || typeof query !== 'string') {
    return res.status(400).json({ error: 'query가 필요해요.' });
  }

  const fullList = Array.isArray(exhibitions) ? exhibitions.slice(0, 200) : [];
  const list = retrieveRelevantExhibitions(query, fullList, 24);
  const context = list.map(function (ex) {
    return '- [' + ex.id + '] ' + ex.title + ' · ' + ex.venue + ' (' + ex.region + ') · ' + ex.period +
      ' · ' + ex.genre + ' · ' + (ex.status || '') + ' · 분위기: ' + (ex.mood || '');
  }).join('\n');

  const historyList = Array.isArray(history) ? history.slice(0, 20) : [];
  const historyLine = historyList.length
    ? '\n\n[이 사용자가 이전에 즐겨찾기하거나 본 전시]\n' + historyList.join(', ') + '\n이 취향을 참고해서 비슷한 결의 전시를 우선 추천해도 좋아요.'
    : '';

  function buildPrompt(extraReminder) {
    return '당신은 "미술이 있는 날들"이라는 전시 안내 사이트의 큐레이터입니다.\n' +
      '규칙:\n' +
      '1) 전시를 추천할 때는 반드시 [전시 목록] 안에서만 추천하고, 목록에 없는 전시를 지어내지 마세요.\n' +
      '2) 사용자가 특정 작가나 작품의 전시 여부를 물었는데 [전시 목록]에 없다면, "해당 작가/작품은 지금 등록된 전시 목록에 없어요."처럼 한두 문장으로만 짧게 답하세요.\n' +
      '3) 미술 사조·시대·화가 등 일반적인 미술 지식(공부)을 물어보면, [전시 목록] 제한과 무관하게 [미술사 참고자료]와 알고 있는 지식을 바탕으로 답하세요.\n' +
      '4) 미술/전시와 무관한 질문에는 정중히 미술 이야기만 도와줄 수 있다고 답하세요.\n' +
      '5) 분석 과정, 영어, 개요, 메모, "Output Format" 같은 형식 설명이나 제목을 절대 출력하지 말고, 친근하고 따뜻한 존댓말의 완성된 한국어 문장만 2~4문장으로 바로 출력하세요.\n\n' +
      '예시 (사용자가 "모던아트가 뭐야?"라고 물었을 때 출력해야 할 형식):\n' +
      '모던아트는 19세기 말부터 20세기 중반까지 이어진 근현대 미술 전반을 가리키는 말이에요. 인상주의, 입체주의, 추상표현주의처럼 전통적인 화풍에서 벗어나 새로운 표현을 시도한 흐름들을 포함해요. 대표적인 화가로는 피카소나 마티스를 들 수 있어요.\n' +
      'IDS:\n\n' +
      '[미술사 참고자료]\n' + ART_HISTORY_REFERENCE +
      '\n\n[전시 목록]\n' + context + historyLine +
      '\n\n[사용자 질문]\n' + query +
      '\n\n위 규칙과 예시 형식에 따라 지금 바로 한국어 답변만 작성하세요. 답변 마지막 줄에 추천하는 전시 id들을 "IDS: id1,id2" 형식으로 한 줄 추가하세요 (추천이 없으면 "IDS:" 만 적으세요).' +
      (extraReminder ? '\n\n' + extraReminder : '');
  }

  async function callGemini(prompt) {
    let geminiRes = await fetch(
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
    if (geminiRes.status === 503) {
      // 구글 쪽 모델 일시 과부하(UNAVAILABLE). 짧게 한 번만 재시도.
      await new Promise(function (r) { setTimeout(r, 600); });
      geminiRes = await fetch(
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
    return geminiRes;
  }

  // 형식 설명("Output Format" 등)이 새거나 문장이 안 끝난 채로 잘린 것처럼 보이면 깨진 답으로 간주.
  function looksBroken(answer) {
    if (!answer) return true;
    if (/output format|analyze|analysis|\*\*/i.test(answer)) return true;
    if (answer.length < 6) return true;
    if (!/[.!?요다임음함죠]["')\]]?$/.test(answer)) return true;
    return false;
  }

  function extractAnswer(data) {
    const candidate = (data.candidates && data.candidates[0]) || null;
    const finishReason = candidate && candidate.finishReason;
    const parts = (candidate && candidate.content && candidate.content.parts) || [];
    const text = parts.filter(function (p) { return !p.thought; }).map(function (p) { return p.text || ''; }).join('');
    const idsMatch = text.match(/IDS:\s*(.*)$/m);
    const ids = idsMatch ? idsMatch[1].split(',').map(function (s) { return s.trim(); }).filter(Boolean) : [];
    const answer = text.replace(/IDS:\s*.*/m, '').trim();
    return { finishReason: finishReason || null, text: text, ids: ids, answer: answer, partsCount: parts.length };
  }

  try {
    let geminiRes = await callGemini(buildPrompt());
    if (!geminiRes.ok) {
      const errText = await geminiRes.text();
      console.error('Gemini error', geminiRes.status, errText);
      const msg = geminiRes.status === 503
        ? 'AI가 지금 많이 몰려서 답을 못 가져왔어요. 잠시 후 다시 시도해주실래요?'
        : 'AI 응답을 가져오지 못했어요.';
      return res.status(502).json({ error: msg });
    }
    let data = await geminiRes.json();
    let result = extractAnswer(data);
    if (result.finishReason && result.finishReason !== 'STOP') {
      console.error('Gemini finished abnormally', result.finishReason);
      return res.json({ answer: '죄송해요, 답변 분량이 초과되어 내용을 정리하지 못했어요. 조금 더 짧게 다시 물어봐주실래요?', ids: [] });
    }

    var retried = false;
    if (looksBroken(result.answer)) {
      retried = true;
      geminiRes = await callGemini(buildPrompt('(다시 한번: 형식 설명이나 영어 문구, "**" 같은 마크다운 없이 오직 완성된 한국어 문장 2~4개만 바로 출력하세요.)'));
      if (geminiRes.ok) {
        data = await geminiRes.json();
        const retryResult = extractAnswer(data);
        if (!retryResult.finishReason || retryResult.finishReason === 'STOP') {
          result = retryResult;
        }
      }
    }

    res.json({
      answer: (!looksBroken(result.answer) && result.answer) || '음, 지금은 답을 정리하지 못했어요. 다시 물어봐주실래요?',
      ids: result.ids,
      debug: { finishReason: result.finishReason, rawTextLength: result.text.length, partsCount: result.partsCount, retrievedCount: list.length, totalCount: fullList.length, retried: retried }
    });
  } catch (err) {
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

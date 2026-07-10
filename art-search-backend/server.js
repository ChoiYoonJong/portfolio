const express = require('express');
const cors = require('cors');
const { createClient } = require('@supabase/supabase-js');

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
  const idToken = header.startsWith('Bearer ') ? header.slice(7) : null;
  const user = await verifyGoogleToken(idToken);
  if (!user) {
    return res.status(401).json({ error: '로그인이 필요해요.' });
  }
  req.user = user;
  next();
}

// ---------- 하루 사용량 제한 (메모리 기반, 서버 재시작 시 초기화) ----------
var usageByEmail = new Map();
function checkAndBumpUsage(email) {
  var today = new Date().toISOString().slice(0, 10);
  var entry = usageByEmail.get(email);
  if (!entry || entry.day !== today) {
    entry = { day: today, count: 0 };
  }
  entry.count += 1;
  usageByEmail.set(email, entry);
  return entry.count <= DAILY_CURATOR_LIMIT;
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

  const list = Array.isArray(exhibitions) ? exhibitions.slice(0, 80) : [];
  const context = list.map(function (ex) {
    return '- [' + ex.id + '] ' + ex.title + ' · ' + ex.venue + ' (' + ex.region + ') · ' + ex.period +
      ' · ' + ex.genre + ' · ' + (ex.status || '') + ' · 분위기: ' + (ex.mood || '');
  }).join('\n');

  const historyList = Array.isArray(history) ? history.slice(0, 20) : [];
  const historyLine = historyList.length
    ? '\n\n[이 사용자가 이전에 즐겨찾기하거나 본 전시]\n' + historyList.join(', ') + '\n이 취향을 참고해서 비슷한 결의 전시를 우선 추천해도 좋아요.'
    : '';

  const prompt = '당신은 "미술이 있는 날들"이라는 전시 안내 사이트의 큐레이터입니다. 아래는 지금 사이트에 실제로 등록된 전시 목록입니다. ' +
    '반드시 이 목록 안에서만 추천하고, 목록에 없는 전시를 지어내지 마세요. ' +
    '사용자가 물어본 작가나 작품이 이 목록에 없다면, 절대 당신이 알고 있는 외부 지식으로 그 작가·작품을 설명하거나 추측하지 마세요. ' +
    '그런 경우엔 "해당 작가/작품은 지금 등록된 전시 목록에 없어요."처럼 한두 문장으로만 짧게 답하고, 목록 항목을 나열하거나 장황하게 설명하지 마세요. ' +
    '미술/전시와 무관한 질문에는 정중히 미술 이야기만 도와줄 수 있다고 답하세요. 친근하고 따뜻한 존댓말로, 2~4문장 이내로 짧게 답하세요.\n\n' +
    '[전시 목록]\n' + context + historyLine +
    '\n\n[사용자 질문]\n' + query +
    '\n\n답변 마지막 줄에 추천하는 전시 id들을 "IDS: id1,id2" 형식으로 한 줄 추가하세요 (추천이 없으면 "IDS:" 만 적으세요).';

  try {
    let geminiRes = await fetch(
      'https://generativelanguage.googleapis.com/v1beta/models/' + GEMINI_MODEL + ':generateContent?key=' + GEMINI_API_KEY,
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          contents: [{ parts: [{ text: prompt }] }],
          generationConfig: { temperature: 0.4, maxOutputTokens: 500 }
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
            generationConfig: { temperature: 0.4, maxOutputTokens: 500 }
          })
        }
      );
    }
    if (!geminiRes.ok) {
      const errText = await geminiRes.text();
      console.error('Gemini error', geminiRes.status, errText);
      const msg = geminiRes.status === 503
        ? 'AI가 지금 많이 몰려서 답을 못 가져왔어요. 잠시 후 다시 시도해주실래요?'
        : 'AI 응답을 가져오지 못했어요.';
      return res.status(502).json({ error: msg });
    }
    const data = await geminiRes.json();
    const candidate = (data.candidates && data.candidates[0]) || null;
    const finishReason = candidate && candidate.finishReason;
    if (finishReason && finishReason !== 'STOP') {
      console.error('Gemini finished abnormally', finishReason);
      return res.json({ answer: '죄송해요, 답변 분량이 초과되어 내용을 정리하지 못했어요. 조금 더 짧게 다시 물어봐주실래요?', ids: [] });
    }
    const parts = (candidate && candidate.content && candidate.content.parts) || [];
    const text = parts.map(function (p) { return p.text || ''; }).join('');
    const idsMatch = text.match(/IDS:\s*(.*)$/m);
    const ids = idsMatch ? idsMatch[1].split(',').map(function (s) { return s.trim(); }).filter(Boolean) : [];
    const answer = text.replace(/IDS:\s*.*/m, '').trim();
    res.json({ answer: answer || '음, 지금은 답을 정리하지 못했어요. 다시 물어봐주실래요?', ids: ids });
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

app.listen(PORT, () => {
  console.log(`art-search-backend listening on port ${PORT}`);
});

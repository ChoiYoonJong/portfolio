const express = require('express');
const cors = require('cors');

const app = express();
const PORT = process.env.PORT || 3000;
const NAVER_CLIENT_ID = process.env.NAVER_CLIENT_ID;
const NAVER_CLIENT_SECRET = process.env.NAVER_CLIENT_SECRET;
const ALLOWED_ORIGIN = process.env.ALLOWED_ORIGIN || '*';

app.use(cors({ origin: ALLOWED_ORIGIN }));

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

app.listen(PORT, () => {
  console.log(`art-search-backend listening on port ${PORT}`);
});

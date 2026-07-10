"""국립현대미술관(MMCA) 진행중/예정 전시 크롤러.

대상을 국립현대미술관으로 정한 이유: 인터파크 티켓(tickets.interpark.com)은
robots.txt에 `User-agent: *` / `Disallow: /` 로 일반 크롤러의 전체 사이트 접근을
명시적으로 금지하고 있어 대상에서 제외했다. MMCA는 robots.txt가 일부 경로만
제한하고(https://www.mmca.go.kr/robots.txt), 우리가 쓰는 /exhibitions/ 경로는
막혀있지 않다.

두 가지 수집 엔진을 제공한다.
  - requests  (기본) : MMCA가 자체적으로 쓰는 JSON API(AjaxExhibitionList.do)를
    그대로 호출한다. 가장 빠르고 안정적이다.
  - selenium          : 헤드리스 브라우저로 목록 페이지를 실제 렌더링한 뒤
    BeautifulSoup으로 DOM을 파싱한다. requests 엔진이 막히거나(차단/구조 변경)
    JS 렌더링이 필요한 경우를 위한 대안 경로.

사용 예:
  python crawler.py
  python crawler.py --engine selenium
  python crawler.py --status ongoing --out-dir ./backup
"""
from __future__ import annotations

import argparse
import json
import time
from datetime import datetime, timezone, timedelta
from pathlib import Path

import requests
from bs4 import BeautifulSoup

BASE_URL = "https://www.mmca.go.kr"
AJAX_ENDPOINT = f"{BASE_URL}/exhibitions/AjaxExhibitionList.do"
LIST_PAGES = {
    "1": f"{BASE_URL}/exhibitions/progressList.do",       # 진행중
    "2": f"{BASE_URL}/exhibitions/futureProgressList.do",  # 예정
}
STATUS_LABELS = {"1": "진행중", "2": "예정"}
KST = timezone(timedelta(hours=9))

# 사이트 운영자가 요청 주체를 알아볼 수 있도록 연락처를 남긴다.
# HTTP 헤더는 latin-1만 허용되므로 한글 없이 ASCII로만 작성한다.
USER_AGENT = (
    "ArtDaysExhibitionCrawler/1.0 "
    "(personal portfolio project; contact: jongchoi881109@gmail.com)"
)
REQUEST_DELAY_SEC = 0.6
REQUEST_TIMEOUT = 10


def strip_html(raw_html: str) -> str:
    if not raw_html:
        return ""
    text = BeautifulSoup(raw_html, "html.parser").get_text(separator=" ", strip=True)
    return " ".join(text.split())


def normalize(item: dict, exh_flag: str) -> dict:
    poster = item.get("exhThumbImg") or item.get("exhDidImg") or ""
    if poster and not poster.startswith("http"):
        poster = BASE_URL + poster

    venue_branch = (item.get("exhPlaNm") or "").strip()
    start = (item.get("exhStDt") or "").strip()
    end = (item.get("exhEdDt") or "").strip()

    return {
        "id": item.get("exhId"),
        "title": (item.get("exhTitle") or "").strip(),
        "artist": (item.get("exhArtist") or "").strip(),
        "venue": f"국립현대미술관 {venue_branch}".strip(),
        "venue_detail": (item.get("exhPlaDtl") or "").strip(),
        "period_start": start,
        "period_end": end,
        "period_text": f"{start} ~ {end}" if start or end else "",
        "status": STATUS_LABELS.get(str(exh_flag), ""),
        "admission": (item.get("exhAdm") or "").strip(),
        "poster_url": poster,
        "description": strip_html(item.get("exhContents", ""))[:400],
        "source": "국립현대미술관(MMCA)",
        "source_list_url": LIST_PAGES.get(str(exh_flag), BASE_URL),
    }


# ---------- 엔진 1: requests + MMCA JSON API ----------
def crawl_requests(exh_flags, max_pages: int) -> list[dict]:
    session = requests.Session()
    session.headers.update({"User-Agent": USER_AGENT})
    # 목록 페이지를 한 번 방문해 정상적인 세션 쿠키를 먼저 확보한다.
    session.get(LIST_PAGES["1"], timeout=REQUEST_TIMEOUT)

    results: list[dict] = []
    seen_ids: set[str] = set()

    for exh_flag in exh_flags:
        page = 1
        while page <= max_pages:
            resp = session.post(
                AJAX_ENDPOINT,
                data={
                    "exhFlag": exh_flag,
                    "searchExhPlaCd": "",
                    "searchExhCd": "",
                    "sort": "1",
                    "pageIndex": page,
                },
                headers={"X-Requested-With": "XMLHttpRequest"},
                timeout=REQUEST_TIMEOUT,
            )
            resp.raise_for_status()
            data = resp.json()
            items = data.get("exhibitionsList") or []
            if not items:
                break

            for item in items:
                norm = normalize(item, exh_flag)
                if norm["id"] in seen_ids:
                    continue
                seen_ids.add(norm["id"])
                results.append(norm)

            total_pages = (data.get("paginationInfo") or {}).get("totalPageCount", page)
            if page >= total_pages:
                break
            page += 1
            time.sleep(REQUEST_DELAY_SEC)

    return results


# ---------- 엔진 2: selenium(헤드리스 브라우저) + BeautifulSoup ----------
def crawl_selenium(exh_flags, max_pages: int) -> list[dict]:
    from selenium import webdriver
    from selenium.webdriver.chrome.options import Options
    from selenium.webdriver.common.by import By
    from selenium.webdriver.support.ui import WebDriverWait
    from selenium.webdriver.support import expected_conditions as EC

    options = Options()
    options.add_argument("--headless=new")
    options.add_argument("--disable-gpu")
    options.add_argument(f"user-agent={USER_AGENT}")
    driver = webdriver.Chrome(options=options)

    results: list[dict] = []
    seen_ids: set[str] = set()

    try:
        for exh_flag in exh_flags:
            url = LIST_PAGES.get(str(exh_flag))
            if not url:
                continue
            driver.get(url)
            WebDriverWait(driver, 15).until(
                EC.presence_of_element_located((By.ID, "listDiv"))
            )
            time.sleep(0.5)  # 최초 AJAX 로드 여유시간

            page = 1
            while page <= max_pages:
                soup = BeautifulSoup(driver.page_source, "html.parser")
                cards = soup.select("#listDiv li")
                if not cards:
                    break

                page_had_new = False
                for card in cards:
                    onclick = ""
                    link = card.select_one("a[onclick]")
                    if link:
                        onclick = link.get("onclick", "")
                    # onclick="javascript:fn_Detail('202601060002026')" 에서 id 추출
                    exh_id = None
                    if "fn_Detail(" in onclick:
                        exh_id = onclick.split("fn_Detail(")[1].split(")")[0].strip("'\"")
                    if not exh_id or exh_id in seen_ids:
                        continue
                    seen_ids.add(exh_id)
                    page_had_new = True

                    img = card.select_one("figure.thumb img")
                    poster = img.get("src", "") if img else ""
                    if poster and not poster.startswith("http"):
                        poster = BASE_URL + poster

                    venue_branch = (card.select_one(".info .ctg") or {}).get_text(strip=True) \
                        if card.select_one(".info .ctg") else ""
                    title = (card.select_one(".info .tit") or {}).get_text(strip=True) \
                        if card.select_one(".info .tit") else ""
                    period_text = (card.select_one(".info .txt") or {}).get_text(strip=True) \
                        if card.select_one(".info .txt") else ""
                    start, _, end = period_text.partition("~")

                    results.append({
                        "id": exh_id,
                        "title": title,
                        "artist": "",
                        "venue": f"국립현대미술관 {venue_branch}".strip(),
                        "venue_detail": "",
                        "period_start": start.strip(),
                        "period_end": end.strip(),
                        "period_text": period_text,
                        "status": STATUS_LABELS.get(str(exh_flag), ""),
                        "admission": "",
                        "poster_url": poster,
                        "description": "",
                        "source": "국립현대미술관(MMCA)",
                        "source_list_url": url,
                    })

                if not page_had_new:
                    break

                # 다음 페이지로 이동 (사이트 자체 페이징 함수 재사용)
                next_page = page + 1
                has_more = driver.execute_script(
                    "if (typeof fn_egov_link_page === 'function') {"
                    "  fn_egov_link_page(arguments[0]); return true;"
                    "} return false;",
                    next_page,
                )
                if not has_more:
                    break
                time.sleep(0.8)  # 페이지 전환 AJAX 대기
                page += 1
    finally:
        driver.quit()

    return results


def save_backup(items: list[dict], out_dir: Path) -> Path:
    out_dir.mkdir(parents=True, exist_ok=True)
    now = datetime.now(KST)
    payload = {
        "generated_at": now.isoformat(),
        "source": "국립현대미술관(MMCA)",
        "count": len(items),
        "items": items,
    }
    dated_path = out_dir / f"backup_{now.strftime('%Y%m%d')}.json"
    latest_path = out_dir / "latest.json"
    text = json.dumps(payload, ensure_ascii=False, indent=2)
    dated_path.write_text(text, encoding="utf-8")
    latest_path.write_text(text, encoding="utf-8")
    return dated_path


def main():
    parser = argparse.ArgumentParser(description="MMCA 진행중/예정 전시 크롤러")
    parser.add_argument("--engine", choices=["requests", "selenium"], default="requests")
    parser.add_argument(
        "--status", choices=["ongoing", "upcoming", "both"], default="both",
        help="ongoing=진행중만, upcoming=예정만, both=둘 다(기본값)",
    )
    parser.add_argument("--max-pages", type=int, default=20, help="상태별 최대 수집 페이지 수")
    parser.add_argument(
        "--out-dir", type=Path, default=Path(__file__).parent / "backup",
        help="백업 JSON을 저장할 폴더 (기본: ./backup)",
    )
    args = parser.parse_args()

    exh_flags = {"ongoing": ["1"], "upcoming": ["2"], "both": ["1", "2"]}[args.status]
    crawl_fn = crawl_requests if args.engine == "requests" else crawl_selenium

    print(f"[crawler] engine={args.engine} status={args.status} 수집을 시작합니다...")
    items = crawl_fn(exh_flags, args.max_pages)
    print(f"[crawler] 총 {len(items)}건 수집 완료.")

    out_path = save_backup(items, args.out_dir)
    print(f"[crawler] 저장 완료: {out_path}")


if __name__ == "__main__":
    main()

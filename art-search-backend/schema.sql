-- Supabase SQL editor에서 한 번 실행해주세요.
-- 즐겨찾기·방문기록을 저장하는 단일 테이블입니다.

create table if not exists user_activity (
  id bigint generated always as identity primary key,
  email text not null,
  exhibit_id text not null,
  type text not null check (type in ('favorite', 'visit')),
  created_at timestamptz not null default now(),
  unique (email, exhibit_id, type)
);

create index if not exists user_activity_email_idx on user_activity (email);

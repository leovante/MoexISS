--liquibase formatted sql
--changeset temnikov_do:securities_candles_count

CREATE VIEW securities_count_per_day AS
with m1 as (SELECT mm.begin_at::date as date, count(mm) as count
            from securities_candles_m1 mm
            group by mm.begin_at::date),
     m10 as (SELECT mm.begin_at::date as date, count(mm) as count
             from securities_candles_m10 mm
             group by mm.begin_at::date),
     m60 as (SELECT mm.begin_at::date as date, count(mm) as count
             from securities_candles_m60 mm
             group by mm.begin_at::date),
     d1 as (SELECT mm.begin_at::date as date, count(mm) as count
            from securities_candles_d1 mm
            group by mm.begin_at::date),
     d7 as (SELECT mm.begin_at::date as date, count(mm) as count
            from securities_candles_d7 mm
            group by mm.begin_at::date),
     d31 as (SELECT mm.begin_at::date as date, count(mm) as count
             from securities_candles_d31 mm
             group by mm.begin_at::date)
select coalesce(m1.date, m10.date, m60.date, d1.date, d7.date, d31.date) as date,
       m1.count                                                          as m1,
       m10.count                                                         as m10,
       m60.count                                                         as m60,
       d1.count                                                          as d1,
       d7.count                                                          as d7,
       d31.count                                                         as d31
from m1
         full join m10 on m1.date = m10.date
         full join m60 on m1.date = m60.date
         full join d1 on m1.date = d1.date
         full join d7 on m1.date = d7.date
         full join d31 on m1.date = d31.date
order by date;
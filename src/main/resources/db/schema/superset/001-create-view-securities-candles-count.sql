--liquibase formatted sql
--changeset temnikov_do:securities_candles_count

CREATE VIEW securities_candles_count AS
with m1 as (SELECT mm.sec_id as sec, count(mm.sec_id) as count from securities_candles_m1 mm group by mm.sec_id),
    m10 as (SELECT mm.sec_id as sec, count(mm.sec_id) as count from securities_candles_m10 mm group by mm.sec_id),
    m60 as (SELECT mm.sec_id as sec, count(mm.sec_id) as count from securities_candles_m60 mm group by mm.sec_id),
    d1 as (SELECT mm.sec_id as sec, count(mm.sec_id) as count from securities_candles_d1 mm group by mm.sec_id),
    d7 as (SELECT mm.sec_id as sec, count(mm.sec_id) as count from securities_candles_d7 mm group by mm.sec_id),
    d31 as (SELECT mm.sec_id as sec, count(mm.sec_id) as count from securities_candles_d31 mm group by mm.sec_id)
select m1.sec    as sec,
       m1.count  as m1,
       m10.count as m10,
       m60.count as m60,
       d1.count  as d1,
       d7.count  as d7,
       d31.count as d31
from m1
         left join m10 on m1.sec = m10.sec
         left join m60 on m1.sec = m60.sec
         left join d1 on m1.sec = d1.sec
         left join d7 on m1.sec = d7.sec
         left join d31 on m1.sec = d31.sec;
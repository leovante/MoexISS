--liquibase formatted sql
--changeset temnikov_do:securities_candles_count

CREATE VIEW securities_count_per_day AS
with init as (select *
              from securities_candles_d1 sec
              order by sec_id desc, begin_at),
     lag as (select *,
                    lag(sec.close, 1) over (partition by sec.sec_id order by sec.begin_at)    as prev_close,
                    lag(sec.sec_id, 1) over (partition by sec.sec_id order by sec.begin_at)   as prev_name,
                    lag(sec.begin_at, 1) over (partition by sec.sec_id order by sec.begin_at) as prev_begin
             from init sec
             order by sec_id desc, begin_at desc),
     calc as (select greatest(abs(lag.high - lag.low),
                              abs(lag.high - lag.prev_close),
                              abs(lag.prev_close - lag.low))::double precision as atr,
                     (open + close) / 2                                        as avg,
                     *
              from lag
              where prev_close notnull
                and begin_at::TIMESTAMP - prev_begin::TIMESTAMP < INTERVAL '5 DAYS'),
     calc2 as (select (1 - ((avg - atr) / avg)) * 100 as atr_percent,
                      *
               from calc),
     sec_top_atr as (select distinct(c2.sec_id) as sec_id,
                                    c2.atr_percent
                     from (select *
                           from calc2 c3
                           where c3.begin_at >= now() - interval '7 DAYS'
                           order by c3.atr_percent desc) as c2
                     limit 20),
     sec_top_value as (select distinct(c2.sec_id) as sec_id,
                                      c2.value
                       from (select *
                             from calc2 c3
                             order by c3.value desc) as c2
                       limit 20)
select c2.atr,
       c2.atr_percent,
       c2.sec_id,
       c2.begin_at::date,
       c2.open,
       c2.close,
       c2.high,
       c2.low,
       c2.value,
       c2.volume/*,
       c2.prev_close,
       c2.prev_name,
       c2.prev_begin*/
from calc2 c2
         inner join sec_top_value st on c2.sec_id = st.sec_id
order by atr_percent desc, sec_id;
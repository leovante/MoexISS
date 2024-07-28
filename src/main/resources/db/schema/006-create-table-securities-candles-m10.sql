--liquibase formatted sql
--changeset temnikov_do:001-create-table-securities-candles

create table securities_candles_m10
(
    sec_id   varchar(51)                 not null,
    begin_at timestamp without time zone not null,
    end_at   timestamp without time zone not null,
    open     double precision            not null,
    close    double precision            not null,
    high     double precision            not null,
    low      double precision            not null,
    value    double precision            not null,
    volume   double precision            not null,
    constraint securities_candles_m10_pk primary key (sec_id, begin_at, end_at)

);

comment on table securities_candles_m10 is '10 минутные свечи';

create index securities_candles_m10_securities_candles_m10_pk_idx
    on securities_candles_m10 (sec_id, begin_at, end_at);
create index securities_candles_m10_securities_candles_m10_sec_id
    on securities_candles_m10 (sec_id);
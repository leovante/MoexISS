--liquibase formatted sql
--changeset temnikov_do:001-create-table-securities-trades

create table securities_trades
(
    trade_no        BIGSERIAL                   not null
        constraint securities_trades_trade_no_pk primary key,
    trade_time      time without time zone      not null,
    board_if        varchar(255)                not null,
    sec_id          varchar(51)                 not null,
    price           double precision            not null,
    quantity        double precision            not null,
    value           double precision            not null,
    period          varchar(255)                not null,
    trade_time_grp  bigint                      not null,
    sys_time        timestamp without time zone not null,
    buy_sell        varchar(3)                  not null,
    trading_session varchar(10)                 not null
);

comment on table securities_trades is 'выполненные заявки';
comment on column securities_trades.trade_no is 'уникальный идентификатор';

create index securities_trades_securities_trades_pk
    on securities_trades (trade_no);
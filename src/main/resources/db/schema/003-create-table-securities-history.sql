--liquibase formatted sql
--changeset temnikov_do:001-create-table-securities_history

create table securities_history
(
    id                         BIGSERIAL        not null
        constraint securities_history_pk primary key,
    board_id                   varchar(10)      not null,
    trade_date                 date             not null,
    short_name                 varchar(100)     not null,
    sec_id                     varchar(51)      not null,
    num_trades                 bigint           not null,
    value                      double precision not null,
    open                       double precision not null,
    low                        double precision not null,
    high                       double precision not null,
    legal_close_price          double precision not null,
    wa_price                   double precision not null,
    close                      double precision not null,
    volume                     double precision not null,
    market_price2              double precision not null,
    market_price3              double precision not null,
    admin_ted_quote            double precision not null,
    mp2valtrd                  double precision not null,
    market_price3_trades_value double precision not null,
    admitted_value             double precision not null,
    waval                      double precision not null,
    trading_session            int              not null,
    currency_id                varchar(10)      not null,
    trendclspr                 double precision not null

);

comment on table securities_history is 'история выполненных заявок';
comment on column securities_history.id is 'уникальный идентификатор';

create index securities_history_securities_history_pk
    on securities_history (id);
create index securities_history_securities_history_sec_id
    on securities_history (sec_id);
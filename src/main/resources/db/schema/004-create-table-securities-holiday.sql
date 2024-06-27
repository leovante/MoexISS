--liquibase formatted sql
--changeset temnikov_do:001-create-table-securities-holiday

create table securities_holiday
(
    id                         BIGSERIAL        not null
        constraint securities_holiday_pkey primary key,
    board_id                   varchar(255)     not null,
    trade_date                 date             not null,
    sec_id                     varchar(255)     not null

);

comment on table securities_holiday is 'неторгуемые дни у актива';
comment on column securities_holiday.id is 'уникальный идентификатор';

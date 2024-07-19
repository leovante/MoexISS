--liquibase formatted sql
--changeset temnikov_do:001-create-table-securities-holiday

create table securities_holiday
(
    id         BIGSERIAL   not null
        constraint securities_holiday_pk primary key,
    board_id   varchar(10) not null,
    trade_date date        not null,
    sec_id     varchar(51) not null

);

comment on table securities_holiday is 'неторгуемые дни у актива';
comment on column securities_holiday.id is 'уникальный идентификатор';

create index securities_holiday_securities_holiday_pk
    on securities_holiday (id);
create index securities_holiday_securities_holiday_sec_id
    on securities_holiday (sec_id);
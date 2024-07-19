--liquibase formatted sql
--changeset temnikov_do:002-create-table-moex_sync

create table moex_sync
(
    id          BIGSERIAL                   not null
        constraint moex_sync_pk primary key,
    data_type   varchar(20)                 not null,
    update_date timestamp without time zone not null
);

comment on table moex_sync is 'служебная таблица';
comment on column moex_sync.id is 'уникальный идентификатор';

create index moex_sync_moex_sync_pk
    on moex_sync (id);
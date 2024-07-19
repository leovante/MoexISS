--liquibase formatted sql
--changeset temnikov_do:000-create-table-securities

create table securities
(
    id          BIGSERIAL                   not null
        constraint securities_pk primary key,
    sec_id      varchar(51)                 not null,
    create_date timestamp without time zone not null,
    update_date timestamp without time zone not null
);

comment on table securities is 'актив';
comment on column securities.id is 'уникальный идентификатор';

create index securities_securities_pk
    on securities (id);
create index securities_securities_sec_id
    on securities (sec_id);
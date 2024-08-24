--liquibase formatted sql
--changeset temnikov_do:000-create-table-securities

CREATE TABLE RESOURCE_LOCK
(
    ID UUID NOT NULL,
    RESOURCE_NAME character varying(200) NOT NULL,
    DATE_CREATE timestamp with time zone NOT NULL,
    date_expire timestamp with time zone,
    PRIMARY KEY (ID),
    UNIQUE (RESOURCE_NAME)
);

CREATE INDEX resource_lock_date_create_idx ON public.resource_lock USING btree (date_create ASC NULLS LAST);
CREATE INDEX resource_lock_date_expire_idx ON public.resource_lock USING btree (date_expire ASC NULLS LAST);

COMMENT ON TABLE resource_lock IS 'Блокировки ресурсов для синхронизации процессов';
COMMENT ON COLUMN resource_lock.id IS 'Системный идентификатор';
COMMENT ON COLUMN resource_lock.resource_name IS 'Ключ';
COMMENT ON COLUMN resource_lock.date_create IS 'Дата создания';
COMMENT ON COLUMN resource_lock.date_expire IS 'Срок действия';

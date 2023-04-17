--liquibase formatted sql

--changeset eshadrin:alter_link_table
ALTER TABLE link
    ADD COLUMN IF NOT EXISTS updateTime timestamp DEFAULT now();
ALTER TABLE link
    ADD COLUMN IF NOT EXISTS checkTime timestamp DEFAULT now();
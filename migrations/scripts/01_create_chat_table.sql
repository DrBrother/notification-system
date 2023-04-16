--liquibase formatted sql

--changeset eshadrin:create_chat_table
CREATE TABLE IF NOT EXISTS chat
(
    id BIGINT PRIMARY KEY
);
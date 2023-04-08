--liquibase formatted sql

--changeset eshadrin:create_subscription_table
CREATE TABLE IF NOT EXISTS subscription
(
    chat_id BIGINT REFERENCES chat ON DELETE CASCADE,
    link_id BIGINT REFERENCES link ON DELETE RESTRICT,
    PRIMARY KEY (chat_id, link_id)
);
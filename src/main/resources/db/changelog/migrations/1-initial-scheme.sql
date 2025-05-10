--liquibase formatted sql

CREATE SCHEMA IF NOT EXISTS "product";
SET search_path TO "product";

--changeset Andrey Antonov:1
--comment create table unit
CREATE TABLE IF NOT EXISTS unit
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(16) NOT NULL
);

--changeset Andrey Antonov:2
--comment create table product
CREATE TABLE IF NOT EXISTS product
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(64)                  NOT NULL,
    unit_id SERIAL REFERENCES unit (id) NOT NULL,
    UNIQUE (name, unit_id)
);

--changeset Andrey Antonov:3
--comment create table product_picture
CREATE TABLE IF NOT EXISTS product_picture
(
    id      BIGSERIAL PRIMARY KEY,
    product_id    BIGSERIAL REFERENCES product (id) NOT NULL,
    url            VARCHAR(128),
    file_extension VARCHAR(8)   NOT NULL,
    size           INTEGER      NOT NULL,
    file_name      VARCHAR(255) NOT NULL,
    mime_type      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

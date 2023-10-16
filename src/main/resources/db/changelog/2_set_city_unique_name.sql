--liquibase formatted sql

--changeset alex.zaitsev:2 endDelimiter:/
ALTER TABLE city
ADD UNIQUE (name)
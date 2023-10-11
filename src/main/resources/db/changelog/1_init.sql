--liquibase formatted sql

--changeset alex.zaitsev:1 endDelimiter:/

CREATE TABLE city(
                     id BIGSERIAL PRIMARY KEY,
                     name varchar (100) NOT NULL,
                     latitude double precision NOT NULL ,
                     longitude double precision NOT NULL
);
CREATE TABLE distance(
                         from_city BIGINT NOT NULL,
                         to_city BIGINT NOT NULL,
                         distance BIGINT NOT NULL,
                         PRIMARY KEY (from_city, to_city),
                         FOREIGN KEY (from_city) REFERENCES city(id),
                         FOREIGN KEY (to_city) REFERENCES city(id)
);
CREATE TABLE IF NOT EXISTS person (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    surname       VARCHAR(255) NOT NULL,
    gender        VARCHAR(20)  NOT NULL,
    birth_date    DATE         NOT NULL,
    contact_type  VARCHAR(50)  NOT NULL,
    contact_value VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP    NOT NULL,
    updated_at    TIMESTAMP,
    deleted       BOOLEAN DEFAULT FALSE
);
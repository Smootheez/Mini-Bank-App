CREATE TABLE users
(
    id                 BIGSERIAL PRIMARY KEY,

    email              VARCHAR(255) NOT NULL UNIQUE,
    first_name         VARCHAR(255) NOT NULL,
    last_name          VARCHAR(255) NOT NULL,
    password           VARCHAR(255) NOT NULL,
    pin                VARCHAR(255) NOT NULL,

    balance            DECIMAL(19, 2) NOT NULL,
    currency           VARCHAR(255) NOT NULL,

    role               VARCHAR(255) NOT NULL,
    status             VARCHAR(255) NOT NULL,

    created_date       TIMESTAMPTZ  NOT NULL,
    last_modified_date TIMESTAMPTZ  NOT NULL,
    created_by         VARCHAR(255) NOT NULL,
    last_modified_by   VARCHAR(255) NOT NULL,
    version            INTEGER      NOT NULL
);

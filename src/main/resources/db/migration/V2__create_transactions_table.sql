CREATE TABLE transactions
(
    id               BIGSERIAL PRIMARY KEY,

    transaction_id    VARCHAR(255)   NOT NULL UNIQUE,
    receiver_email    VARCHAR(255),
    receiver_name     VARCHAR(255),

    amount            DECIMAL(19, 2) NOT NULL,
    currency          VARCHAR(255)   NOT NULL,
    type              VARCHAR(255)   NOT NULL,

    created_by        VARCHAR(255)   NOT NULL,
    transaction_date  TIMESTAMPTZ    NOT NULL,

    email             VARCHAR(255)   NOT NULL,
    CONSTRAINT fk_transactions_users FOREIGN KEY (email) REFERENCES users (email)
);

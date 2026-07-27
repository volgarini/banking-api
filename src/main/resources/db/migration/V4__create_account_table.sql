CREATE TYPE account_type AS ENUM (
    'CHECKING',
    'SAVINGS'
);

CREATE TYPE account_status AS ENUM (
    'ACTIVE',
    'BLOCKED',
    'CLOSED'
);

CREATE TABLE account
(
    id UUID PRIMARY KEY,

    customer_id UUID NOT NULL,

    account_number VARCHAR(20) NOT NULL UNIQUE,

    iban VARCHAR(34) NOT NULL UNIQUE,

    account_type account_type NOT NULL,

    status account_status NOT NULL DEFAULT 'ACTIVE',

    balance NUMERIC(19,2) NOT NULL DEFAULT 0.00,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer(id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_account_customer
    ON account(customer_id);

CREATE INDEX idx_account_status
    ON account(status);

CREATE INDEX idx_account_type
    ON account(account_type);
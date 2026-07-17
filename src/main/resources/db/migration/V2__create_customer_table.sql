CREATE TABLE customer (

    id BIGSERIAL PRIMARY KEY,
    document_type_id BIGINT NOT NULL REFERENCES document_type(id),
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
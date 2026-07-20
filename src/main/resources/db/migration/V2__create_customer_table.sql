CREATE TABLE customer (

    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_type_id UUID NOT NULL REFERENCES document_type(id),
    document_number VARCHAR(40) UNIQUE NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
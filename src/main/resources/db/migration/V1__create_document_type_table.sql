CREATE TABLE document_type (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(30) NOT NULL UNIQUE,
    description VARCHAR(100) NOT NULL
);
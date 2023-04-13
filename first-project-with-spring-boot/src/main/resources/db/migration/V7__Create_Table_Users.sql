CREATE TABLE "user" (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    account_non_expired BOOLEAN NOT NULL,
    account_non_locked BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    enabled BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);
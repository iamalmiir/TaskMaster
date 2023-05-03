CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users
(
    id         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP        DEFAULT NOW(),
    updated_at TIMESTAMP        DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS tasks
(
    id          uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    user_id     uuid         NOT NULL,
    created_at  TIMESTAMP        DEFAULT NOW(),
    updated_at  TIMESTAMP        DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
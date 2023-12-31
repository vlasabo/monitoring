CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username   VARCHAR(200) UNIQUE NOT NULL,
    "password" VARCHAR(200)        NOT NULL
);

CREATE TABLE IF NOT EXISTS role
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id BIGINT REFERENCES users (id) NOT NULL,
    "role"  VARCHAR(20)                  NOT NULL
);

CREATE TABLE facility_level_access
(
    id          SERIAL PRIMARY KEY,
    facility_id INT REFERENCES facility (id) NOT NULL,
    user_id     INT REFERENCES users (id)    NOT NULL
);

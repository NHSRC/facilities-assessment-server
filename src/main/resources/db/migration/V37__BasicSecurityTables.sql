CREATE TABLE role (
  id                 SERIAL PRIMARY KEY,
  name               CHARACTER VARYING(255)                                   NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL DEFAULT 1,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

CREATE TABLE "user" (
  id                 SERIAL PRIMARY KEY,
  email               CHARACTER VARYING(255)                                   NOT NULL,
  first_name               CHARACTER VARYING(255)                                   NOT NULL,
  last_name               CHARACTER VARYING(255)                                   NOT NULL,
  password               CHARACTER VARYING(255)                                   NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL DEFAULT 1,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

CREATE TABLE user_role (
  user_id INT REFERENCES "user" (id)     NOT NULL,
  role_id INT REFERENCES role (id)       NOT NULL
);

INSERT INTO role (name) VALUES ('ADMIN');
INSERT INTO role (name) VALUES ('USER');
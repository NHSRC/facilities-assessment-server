CREATE TABLE entity_type_metadata
(
  id                 SERIAL PRIMARY KEY,
  name               varchar(255)                                             not null,
  type               varchar(100)                                             not null,
  value_date         TIMESTAMP WITHOUT TIME ZONE,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);
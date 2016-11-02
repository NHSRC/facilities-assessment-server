CREATE TABLE region (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE          NOT NULL
);

CREATE TABLE state (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE          NOT NULL,
  region_id          INT REFERENCES region (id)             NOT NULL
);

CREATE TABLE district (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255)                 NOT NULL,
  state_id           INT REFERENCES state (id)              NOT NULL
);

CREATE TABLE facility_type (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE          NOT NULL
);

CREATE TABLE facility (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE          NOT NULL,
  district_id        INT REFERENCES district (id)           NOT NULL,
  facility_type_id   INT REFERENCES facility_type (id)      NOT NULL
);

CREATE TABLE department (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE          NOT NULL
);

CREATE TABLE assessment_type (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE          NOT NULL
);

CREATE TABLE checklist (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE          NOT NULL,
  department_id      INT REFERENCES department (id)         NOT NULL,
  assessment_type_id INT REFERENCES assessment_type (id)    NOT NULL
);

CREATE TABLE area_of_concern (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE          NOT NULL,
  reference          CHARACTER VARYING(255) UNIQUE          NOT NULL
);

CREATE TABLE checklist_area_of_concern (
  area_of_concern_id INT REFERENCES area_of_concern (id) NOT NULL,
  checklist_id       INT REFERENCES checklist (id)       NOT NULL
);

CREATE TABLE standard (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE          NOT NULL,
  reference          CHARACTER VARYING(255) UNIQUE          NOT NULL,
  area_of_concern_id INT REFERENCES area_of_concern (id)    NOT NULL
);

CREATE TABLE measurable_element (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE          NOT NULL,
  reference          CHARACTER VARYING(255) UNIQUE          NOT NULL,
  standard_id        INT REFERENCES standard (id)           NOT NULL
);

CREATE TABLE checkpoint (
  id                    SERIAL PRIMARY KEY,
  created_date          TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  last_modified_date    TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
  uuid                  UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name                  CHARACTER VARYING(255)                 NOT NULL,
  measurable_element_id INT REFERENCES measurable_element (id) NOT NULL,
  checklist_id          INT REFERENCES checklist (id)          NOT NULL,
  is_default            BOOLEAN DEFAULT TRUE                   NOT NULL,
  state_id              INT  DEFAULT NULL REFERENCES state (id)
);
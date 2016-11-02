SET TIME ZONE 'Asia/Calcutta';

CREATE TABLE region (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE                           NOT NULL
);

CREATE TABLE state (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE                           NOT NULL,
  region_id          INT REFERENCES region (id)                              NOT NULL
);

CREATE TABLE district (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255)                                  NOT NULL,
  state_id           INT REFERENCES state (id)                               NOT NULL
);

CREATE TABLE facility_type (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE                           NOT NULL
);

CREATE TABLE facility (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE                           NOT NULL,
  district_id        INT REFERENCES district (id)                            NOT NULL,
  facility_type_id   INT REFERENCES facility_type (id)                       NOT NULL
);

CREATE TABLE department (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE                           NOT NULL
);

CREATE TABLE assessment_type (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE                           NOT NULL
);

CREATE TABLE checklist (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE                           NOT NULL,
  department_id      INT REFERENCES department (id)                          NOT NULL,
  assessment_type_id INT REFERENCES assessment_type (id)                     NOT NULL
);

CREATE TABLE area_of_concern (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE                           NOT NULL,
  reference          CHARACTER VARYING(255) UNIQUE                           NOT NULL
);

CREATE TABLE checklist_area_of_concern (
  area_of_concern_id INT REFERENCES area_of_concern (id) NOT NULL,
  checklist_id       INT REFERENCES checklist (id)       NOT NULL
);

CREATE TABLE standard (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE                           NOT NULL,
  reference          CHARACTER VARYING(255) UNIQUE                           NOT NULL,
  area_of_concern_id INT REFERENCES area_of_concern (id)                     NOT NULL
);

CREATE TABLE measurable_element (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name               CHARACTER VARYING(255) UNIQUE                           NOT NULL,
  reference          CHARACTER VARYING(255) UNIQUE                           NOT NULL,
  standard_id        INT REFERENCES standard (id)                            NOT NULL
);

CREATE TABLE checkpoint (
  id                    SERIAL PRIMARY KEY,
  created_date          TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date    TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid                  UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  name                  CHARACTER VARYING(255)                                  NOT NULL,
  means_of_verification CHARACTER VARYING(1023),
  measurable_element_id INT REFERENCES measurable_element (id)                  NOT NULL,
  checklist_id          INT REFERENCES checklist (id)                           NOT NULL,
  is_default            BOOLEAN DEFAULT TRUE                                    NOT NULL,
  state_id              INT  DEFAULT NULL REFERENCES state (id)
);

CREATE TABLE assessment (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  facility_id        INT REFERENCES facility (id)                            NOT NULL,
  checklist_id       INT REFERENCES checklist (id)                           NOT NULL,
  start_date         TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  end_date           TIMESTAMP WITHOUT TIME ZONE,
  CHECK (end_date :: TIMESTAMP >= start_date :: TIMESTAMP)
);


CREATE TABLE checkpoint_score (
  id                    SERIAL PRIMARY KEY,
  created_date          TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date    TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  assessment_id         INT REFERENCES assessment (id)                          NOT NULL,
  facility_id           INT REFERENCES facility (id)                            NOT NULL,
  checkpoint_id         INT REFERENCES checkpoint (id)                          NOT NULL,
  score                 INT CHECK (score >= 0 AND score <= 2)                   NOT NULL,
  remarks               TEXT,
  mov_observation       BOOLEAN DEFAULT FALSE,
  mov_staff_interview   BOOLEAN DEFAULT FALSE,
  mov_patient_interview BOOLEAN DEFAULT FALSE,
  mov_record_review     BOOLEAN DEFAULT FALSE
);

CREATE TABLE measurable_element_score (
  id                    SERIAL PRIMARY KEY,
  created_date          TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date    TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  assessment_id         INT REFERENCES assessment (id)                          NOT NULL,
  facility_id           INT REFERENCES facility (id)                            NOT NULL,
  measurable_element_id INT REFERENCES measurable_element (id)                  NOT NULL,
  score                 INT                                                     NOT NULL
);


CREATE TABLE standard_score (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  assessment_id      INT REFERENCES assessment (id)                          NOT NULL,
  facility_id        INT REFERENCES facility (id)                            NOT NULL,
  standard_id        INT REFERENCES standard (id)                            NOT NULL,
  score              INT                                                     NOT NULL
);

CREATE TABLE area_of_concern_score (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  assessment_id      INT REFERENCES assessment (id)                          NOT NULL,
  facility_id        INT REFERENCES facility (id)                            NOT NULL,
  area_of_concern_id INT REFERENCES area_of_concern (id)                     NOT NULL,
  score              INT                                                     NOT NULL
);

CREATE TABLE checklist_score (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  assessment_id      INT REFERENCES assessment (id)                          NOT NULL,
  facility_id        INT REFERENCES facility (id)                            NOT NULL,
  checklist_id       INT REFERENCES checklist (id)                           NOT NULL,
  score              INT                                                     NOT NULL
);

CREATE TABLE facility_score (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  assessment_id      INT REFERENCES assessment (id)                          NOT NULL,
  facility_id        INT REFERENCES facility (id)                            NOT NULL,
  score              INT                                                     NOT NULL
);
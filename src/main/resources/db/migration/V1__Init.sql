SET TIME ZONE 'Asia/Calcutta';

CREATE TABLE state (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  name               CHARACTER VARYING(1024) UNIQUE                           NOT NULL
);

CREATE TABLE district (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                   NOT NULL,
  name               CHARACTER VARYING(1024)                                 NOT NULL,
  state_id           INT REFERENCES state (id)                               NOT NULL
);

CREATE TABLE facility_type (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  name               CHARACTER VARYING(1024) UNIQUE                           NOT NULL
);

CREATE TABLE facility (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  name               CHARACTER VARYING(1024) UNIQUE                           NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  district_id        INT REFERENCES district (id)                             NOT NULL,
  facility_type_id   INT REFERENCES facility_type (id)                        NOT NULL
);

CREATE TABLE department (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  name               CHARACTER VARYING(1024) UNIQUE                           NOT NULL
);

CREATE TABLE assessment_type (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  name               CHARACTER VARYING(1024) UNIQUE                           NOT NULL
);

CREATE TABLE assessment_tool (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  name               CHARACTER VARYING(1024) UNIQUE                           NOT NULL
);


CREATE TABLE checklist (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  name               CHARACTER VARYING(1024) UNIQUE                           NOT NULL,
  department_id      INT REFERENCES department (id)                           NOT NULL,
  assessment_tool_id INT REFERENCES assessment_tool (id)                      NOT NULL
);

CREATE TABLE area_of_concern (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                   NOT NULL,
  name               CHARACTER VARYING(1024)                                 NOT NULL,
  reference          CHARACTER VARYING(255)                                  NOT NULL
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
  inactive           BOOLEAN DEFAULT FALSE                                   NOT NULL,
  name               CHARACTER VARYING(1024)                                 NOT NULL,
  reference          CHARACTER VARYING(255)                                  NOT NULL,
  area_of_concern_id INT REFERENCES area_of_concern (id)                     NOT NULL
);

CREATE TABLE measurable_element (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP  NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                  NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                   NOT NULL,
  name               CHARACTER VARYING(1024)                                 NOT NULL,
  reference          CHARACTER VARYING(255)                                  NOT NULL,
  standard_id        INT REFERENCES standard (id)                            NOT NULL
);

CREATE TABLE checkpoint (
  id                    SERIAL PRIMARY KEY,
  created_date          TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        NOT NULL,
  last_modified_date    TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        NOT NULL,
  uuid                  UUID    DEFAULT uuid_generate_v4() UNIQUE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        NOT NULL,
  inactive              BOOLEAN DEFAULT FALSE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         NOT NULL,
  name                  CHARACTER VARYING(255)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        NOT NULL,
  means_of_verification CHARACTER VARYING(1023),
  measurable_element_id INT REFERENCES measurable_element (id)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        NOT NULL,
  is_default            BOOLEAN DEFAULT TRUE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          NOT NULL,
  checklist_id          INT REFERENCES checklist (id)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 NOT NULL,
  state_id              INT  DEFAULT NULL REFERENCES state (id),
  am_observation        BOOLEAN DEFAULT FALSE,
  am_staff_interview    BOOLEAN DEFAULT FALSE,
  am_patient_interview  BOOLEAN DEFAULT FALSE,
  am_record_review      BOOLEAN DEFAULT FALSE
);

CREATE TABLE facility_assessment (
  id                 SERIAL PRIMARY KEY,
  uuid UUID DEFAULT uuid_generate_v4() UNIQUE                                              NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP                      NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP                NOT NULL,
  facility_id        INT REFERENCES facility (id)                                          NOT NULL,
  assessment_tool_id INT REFERENCES assessment_tool (id)                                   NOT NULL,
  start_date         TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP                NOT NULL,
  end_date           TIMESTAMP WITHOUT TIME ZONE,
  CHECK (end_date :: TIMESTAMP >= start_date :: TIMESTAMP)
);


CREATE TABLE checkpoint_score (
  id                     SERIAL PRIMARY KEY,
  uuid UUID DEFAULT uuid_generate_v4() UNIQUE                                                                  NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP                                          NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP                                    NOT NULL,
  facility_assessment_id INT REFERENCES facility_assessment (id)                                               NOT NULL,
  checkpoint_id          INT REFERENCES checkpoint (id)                                                        NOT NULL,
  checklist_id           INT REFERENCES checklist (id)                                                         NOT NULL,
  score                  INT CHECK (score >= 0 AND score <= 2)                                                 NOT NULL,
  remarks                TEXT
);
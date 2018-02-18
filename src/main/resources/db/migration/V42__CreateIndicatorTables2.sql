DROP TABLE indicator;
DROP TABLE indicator_definition;

CREATE TABLE indicator_definition (
  id                 SERIAL PRIMARY KEY,
  name               CHARACTER VARYING(255)                                   NOT NULL,
  data_type          CHARACTER VARYING(255)                                   NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL DEFAULT 1,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

CREATE TABLE indicator (
  id                 SERIAL PRIMARY KEY,
  numeric_value    INTEGER                                                  NULL,
  bool_value    BOOLEAN                                                  NULL,
  date_value    INTEGER                                                  NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  indicator_definition_id INTEGER NOT NULL,
  facility_assessment_id INTEGER NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL DEFAULT 1,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

ALTER TABLE ONLY indicator
  ADD CONSTRAINT indicator_indicator_definition FOREIGN KEY (indicator_definition_id) REFERENCES indicator_definition (id);
ALTER TABLE ONLY indicator
  ADD CONSTRAINT indicator_facility_assessment FOREIGN KEY (facility_assessment_id) REFERENCES facility_assessment (id);

CREATE TABLE indicator_definition (
  id                 SERIAL PRIMARY KEY,
  name               CHARACTER VARYING(255)                                   NOT NULL,
  numerator          CHARACTER VARYING(255)                                   NOT NULL,
  denominator        CHARACTER VARYING(255)                                   NOT NULL,
  formula            CHARACTER VARYING(255)                                   NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL DEFAULT 1,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

CREATE TABLE indicator (
  id                 SERIAL PRIMARY KEY,
  numerator_value    INTEGER                                                  NOT NULL,
  denominator_value  INTEGER                                                  NOT NULL,
  indicator_value    INTEGER                                                  NOT NULL,
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

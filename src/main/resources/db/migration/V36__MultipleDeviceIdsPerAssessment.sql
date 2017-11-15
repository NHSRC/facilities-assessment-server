ALTER TABLE facility_assessment
  DROP COLUMN device_id;
CREATE TABLE facility_assessment_device (
  id                     SERIAL PRIMARY KEY,
  device_id              VARCHAR(255),
  facility_assessment_id INT REFERENCES facility_assessment (id)                               NOT NULL,
  created_date           TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP                NOT NULL,
  last_modified_date     TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP                NOT NULL,
  uuid                   UUID DEFAULT uuid_generate_v4() UNIQUE                                NOT NULL,
  inactive               BOOLEAN DEFAULT FALSE                                                 NOT NULL
);
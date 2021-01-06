CREATE TABLE assessment_metadata
(
  id                 SERIAL PRIMARY KEY,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  name               varchar(255)                                             not null,
  data_type          varchar(100)                                             not null,
  validation_regex   varchar(255),
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

CREATE TABLE assessment_custom_info
(
  id                     SERIAL PRIMARY KEY,
  assessment_metadata_id int not null,
  value_string           varchar(255),
  facility_assessment_id int not null
);
ALTER TABLE ONLY assessment_custom_info
  ADD CONSTRAINT assessment_custom_info_assessment_metadata FOREIGN KEY (assessment_metadata_id) REFERENCES assessment_metadata (id);
ALTER TABLE ONLY assessment_custom_info
  ADD CONSTRAINT assessment_custom_info_facility_assessment FOREIGN KEY (facility_assessment_id) REFERENCES facility_assessment (id);

DO $$
BEGIN
  BEGIN
    alter table facility_assessment add column assessor_name varchar(255);
    EXCEPTION
    WHEN duplicate_column THEN RAISE NOTICE 'column <column_name> already exists in <table_name>.';
  END;
END;
$$;

insert into assessment_metadata (name, data_type)
VALUES ('Assessor name', 'String');
insert into assessment_custom_info (assessment_metadata_id, value_string, facility_assessment_id)
SELECT assessment_metadata.id, assessor_name, facility_assessment.id
from facility_assessment
       join assessment_metadata on assessment_metadata.name = 'Assessor name';

alter table facility_assessment
  drop column assessor_name;
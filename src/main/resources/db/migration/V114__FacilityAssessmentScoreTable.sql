CREATE TABLE facility_assessment_score (
  id                     SERIAL PRIMARY KEY,
  facility_assessment_id INTEGER                                                  NOT NULL,
  score                  INTEGER                                                  NOT NULL,
  created_date           TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date     TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);
ALTER TABLE ONLY facility_assessment_score
  ADD CONSTRAINT facility_assessment_score_facility_assessment FOREIGN KEY (facility_assessment_id) REFERENCES facility_assessment (id);

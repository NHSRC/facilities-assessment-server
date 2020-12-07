CREATE TABLE checklist_only_score (
  id                     SERIAL PRIMARY KEY,
  facility_assessment_id INTEGER                                                  NOT NULL,
  checklist_id           INTEGER                                                  NOT NULL,
  score                  INTEGER                                                  NOT NULL,
  created_date           TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date     TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

ALTER TABLE ONLY checklist_only_score
  ADD CONSTRAINT checklist_only_facility_assessment FOREIGN KEY (facility_assessment_id) REFERENCES facility_assessment (id);
ALTER TABLE ONLY checklist_only_score
  ADD CONSTRAINT checklist_only_score_checklist FOREIGN KEY (checklist_id) REFERENCES checklist (id);
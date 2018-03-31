-- Checklist score broken down by standard (and hence by area of concern too)
CREATE TABLE checklist_score (
  id                     SERIAL PRIMARY KEY,
  checklist_id           INTEGER                                                  NOT NULL,
  standard_id            INTEGER                                                  NOT NULL,
  area_of_concern_id     INTEGER                                                  NOT NULL,
  facility_assessment_id INTEGER                                                  NOT NULL,
  numerator              INTEGER                                                  NOT NULL,
  denominator            INTEGER                                                  NOT NULL,
  created_date           TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date     TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);
ALTER TABLE ONLY checklist_score
  ADD CONSTRAINT checklist_score_checklist FOREIGN KEY (checklist_id) REFERENCES checklist (id);
ALTER TABLE ONLY checklist_score
  ADD CONSTRAINT checklist_score_standard FOREIGN KEY (standard_id) REFERENCES standard (id);
ALTER TABLE ONLY checklist_score
  ADD CONSTRAINT checklist_score_facility_assessment FOREIGN KEY (facility_assessment_id) REFERENCES facility_assessment (id);
ALTER TABLE ONLY checklist_score
  ADD CONSTRAINT checklist_score_area_of_concern FOREIGN KEY (area_of_concern_id) REFERENCES area_of_concern (id);


-- Each standard's score in an assessment across checklists
CREATE TABLE standard_score (
  id                     SERIAL PRIMARY KEY,
  standard_id            INTEGER                                                  NOT NULL,
  facility_assessment_id INTEGER                                                  NOT NULL,
  numerator              INTEGER                                                  NOT NULL,
  denominator            INTEGER                                                  NOT NULL,
  created_date           TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date     TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);
ALTER TABLE ONLY standard_score
  ADD CONSTRAINT standard_score_standard FOREIGN KEY (standard_id) REFERENCES standard (id);
ALTER TABLE ONLY standard_score
  ADD CONSTRAINT standard_score_facility_assessment FOREIGN KEY (facility_assessment_id) REFERENCES facility_assessment (id);


-- Each area-of-concern's score in an assessment across checklists
CREATE TABLE area_of_concern_score (
  id                     SERIAL PRIMARY KEY,
  area_of_concern_id     INTEGER                                                  NOT NULL,
  facility_assessment_id INTEGER                                                  NOT NULL,
  numerator              INTEGER                                                  NOT NULL,
  denominator            INTEGER                                                  NOT NULL,
  created_date           TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date     TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);
ALTER TABLE ONLY area_of_concern_score
  ADD CONSTRAINT area_of_concern_score_area_of_concern FOREIGN KEY (area_of_concern_id) REFERENCES area_of_concern (id);
ALTER TABLE ONLY area_of_concern_score
  ADD CONSTRAINT area_of_concern_score_facility_assessment FOREIGN KEY (facility_assessment_id) REFERENCES facility_assessment (id);
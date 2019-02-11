CREATE TABLE missing_checkpoint
(
  id           SERIAL PRIMARY KEY,
  name         CHARACTER VARYING(255),
  checklist_id INT REFERENCES checklist (id)
);

create table facility_assessment_missing_checkpoint
(
  id                     SERIAL PRIMARY KEY,
  facility_assessment_id INT REFERENCES facility_assessment (id),
  missing_checkpoint_id  INT REFERENCES missing_checkpoint (id)
);
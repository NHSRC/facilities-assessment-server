CREATE TABLE missing_checklist
(
  id           SERIAL PRIMARY KEY,
  name         CHARACTER VARYING(255),
  facility_assessment_id INT REFERENCES facility_assessment (id)
);

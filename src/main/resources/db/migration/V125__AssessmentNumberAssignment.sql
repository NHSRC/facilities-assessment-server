create table assessment_number_assignment
(
    id                 SERIAL PRIMARY KEY,
    facility_id        INT REFERENCES facility (id)        NOT NULL,
    assessment_type_id INT REFERENCES assessment_type (id) NOT NULL,
    user_id            INT REFERENCES users (id)           NOT NULL,
    assessment_number  CHARACTER VARYING(255) UNIQUE       NOT NULL
);

alter table facility_assessment
    add column assessment_number_assignment_id INT REFERENCES assessment_number_assignment (id) NULL;

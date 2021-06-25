alter table facility_assessment
    drop column user_id;

create table facility_assessment_users
(
    id                     SERIAL PRIMARY KEY,
    facility_assignment_id INT REFERENCES facility_assessment (id) NOT NULL,
    user_id                INT REFERENCES users (id)               NOT NULL
);

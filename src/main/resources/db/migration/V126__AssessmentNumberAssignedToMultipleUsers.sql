alter table assessment_number_assignment
    drop column user_id;

create table assessment_number_assignment_users
(
    id                              SERIAL PRIMARY KEY,
    assessment_number_assignment_id INT REFERENCES assessment_number_assignment (id) NOT NULL,
    user_id                         INT REFERENCES users (id)                        NOT NULL
);

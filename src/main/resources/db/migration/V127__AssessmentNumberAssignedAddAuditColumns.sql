alter table assessment_number_assignment
    add created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP NOT NULL,
    add last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP NOT NULL;

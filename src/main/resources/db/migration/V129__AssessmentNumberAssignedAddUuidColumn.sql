alter table assessment_number_assignment
    add uuid UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL;

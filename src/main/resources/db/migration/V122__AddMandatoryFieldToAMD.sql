alter table assessment_metadata add column mandatory boolean default false;
update assessment_metadata set mandatory = true where name = 'Assessor name';
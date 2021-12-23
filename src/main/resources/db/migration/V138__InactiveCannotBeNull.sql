update assessment_number_assignment set inactive = false;
alter table assessment_number_assignment alter column inactive set not null;

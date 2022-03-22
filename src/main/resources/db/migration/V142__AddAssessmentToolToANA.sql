update facility_assessment set assessment_number_assignment_id = null where assessment_number_assignment_id is not null;
delete from assessment_number_assignment_users where 1 = 1;
delete from assessment_number_assignment where 1 = 1;
alter table assessment_number_assignment add column assessment_tool_id int null;
alter table assessment_number_assignment alter column assessment_tool_id set not null;

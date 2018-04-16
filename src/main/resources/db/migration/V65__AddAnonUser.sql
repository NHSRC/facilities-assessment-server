insert into users (email, first_name, last_name, password, inactive, uuid, version, user_type, user_type_reference_id, created_date, last_modified_date)
VALUES ('anonymous@gunak.nhsrcindia.org', 'Anonymous', 'None', 'NA', true, '417275b4-14a3-420b-b5ff-e23068fc1f26', 1, 'AnonymousAssessor', -1, current_timestamp, current_timestamp);
update users set user_type_reference_id = (select id from users where user_type = 'AnonymousAssessor');



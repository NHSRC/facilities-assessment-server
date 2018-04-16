alter table facility_assessment add column user_id int not null default 1;
ALTER TABLE ONLY facility_assessment
  ADD CONSTRAINT facility_assessment_user FOREIGN KEY (user_id) REFERENCES users (id);
update facility_assessment set user_id = (select id from users where user_type = 'AnonymousAssessor');
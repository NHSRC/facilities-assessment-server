
select user0_.id as id1_25_, user0_.created_date as created_2_25_, user0_.last_modified_date as last_mod3_25_, user0_.inactive as inactive4_25_, user0_.uuid as uuid5_25_, user0_.email as email6_25_, user0_.first_name as first_na7_25_, user0_.last_name as last_nam8_25_, user0_.password as password9_25_ from "user" user0_ where user0_.email='petmongrels@gmail.com';

SELECT * from deployment_configuration;
UPDATE deployment_configuration SET recording_mode = true;
UPDATE deployment_configuration SET recording_mode = false;



SELECT * FROM role;

UPDATE users set inactive = true;

select u.email, r.name from users u inner join user_role ur on (u.id = ur.user_id) inner join role r on(ur.role_id = r.id) where u.email='petmongrels@gmail.com';

select email, password, inactive from users where email='petmongrels@gmail.com';



SELECT * from user_role;

select email, password, NOT inactive as active from users;

DELETE from user_role;
DELETE from users;
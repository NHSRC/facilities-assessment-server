insert into privilege (name) values ('Users_Read');
insert into privilege (name) values ('Users_Write');

insert into role_privilege (role_id, privilege_id) values ((select id from role where name = 'ADMIN'), (select id from privilege where name = 'Users_Read'));

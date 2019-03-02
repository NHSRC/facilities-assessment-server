delete from role_privilege where privilege_id in (select id from privilege where name in ('User_Write'));
delete from privilege where name in ('User_Write');

update privilege set name = 'User' where name = 'User_Read';
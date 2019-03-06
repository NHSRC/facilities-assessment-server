alter table users add column system_user BOOLEAN DEFAULT FALSE NOT NULL;
update users set system_user = true, email = 'anonymous@example.com' where email = 'anonymous@gunak.nhsrcindia.org';
insert into users (email, first_name, last_name, password, system_user) values ('backgroundservice@example.com', 'Background', 'Service', 'NA', true);
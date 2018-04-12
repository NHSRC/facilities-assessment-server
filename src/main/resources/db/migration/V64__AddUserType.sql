alter table users add column user_type varchar(50) not null default 'State';
alter table users add column user_type_reference_id int not null default 1;
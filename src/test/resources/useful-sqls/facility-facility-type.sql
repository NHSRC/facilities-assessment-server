begin transaction;
insert into facility_type (name, inactive) values ('Homoeopathy dispensary', true);
commit;

begin transaction;
update facility_type set inactive = true, last_modified_date = current_timestamp where name = 'Ayush Dispensaries';
commit;

begin transaction;
update facility set inactive = true, last_modified_date = current_timestamp
where facility_type_id in (select id from facility_type where name in ('Ayurveda dispensary', 'Ayush Dispensaries'));
commit;

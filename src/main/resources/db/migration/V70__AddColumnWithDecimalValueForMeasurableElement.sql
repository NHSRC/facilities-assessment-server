update checkpoint set measurable_element_id = (select id from measurable_element where reference = 'D3.3' and standard_id = (select standard_id from measurable_element where reference = 'D3,3')) where measurable_element_id = (select id from measurable_element where reference = 'D3,3');
delete from measurable_element where reference = 'D3,3';

update checkpoint set measurable_element_id = (select id from measurable_element where reference = 'C2.3' and standard_id = (select standard_id from measurable_element where reference = 'C2..3')) where measurable_element_id = (select id from measurable_element where reference = 'C2..3');
update checkpoint set measurable_element_id = (select id from measurable_element where reference = 'C2.4' and standard_id = (select standard_id from measurable_element where reference = 'C2..4')) where measurable_element_id = (select id from measurable_element where reference = 'C2..4');

delete from measurable_element where reference = 'C2..3';
delete from measurable_element where reference = 'C2..4';

alter table measurable_element add column ref_num numeric not null default 1;
update measurable_element set ref_num = substring(reference from 2)::numeric;
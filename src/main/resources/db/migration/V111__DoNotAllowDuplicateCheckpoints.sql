alter table checkpoint drop constraint  if exists checkpoint_checklist_id_measurable_element_id_name_inactive_key;
alter table checkpoint add unique (checklist_id, measurable_element_id, name, inactive);
alter table missing_checkpoint add unique (name, checklist_id);
alter table facility_assessment_missing_checkpoint add unique (facility_assessment_id, missing_checkpoint_id);
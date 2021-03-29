delete from checklist_only_score where 1 = 1;
delete from checklist_score where 1 = 1;
delete from standard_score where 1 = 1;
delete from area_of_concern_score where 1 = 1;
delete from facility_assessment_score where 1 = 1;
delete from scoring_process_detail where 1 = 1;
insert into scoring_process_detail (last_scored_until, uuid) values (to_timestamp(0), '6773c069-54d6-40ae-9aca-9bddc2e91d43');
delete from checklist_only_score;
delete from checklist_score;
delete from standard_score;
delete from area_of_concern_score;
delete from facility_assessment_score;
delete from scoring_process_detail;
insert into scoring_process_detail (last_scored_until, uuid) values (to_timestamp(0), '6773c069-54d6-40ae-9aca-9bddc2e91d43');
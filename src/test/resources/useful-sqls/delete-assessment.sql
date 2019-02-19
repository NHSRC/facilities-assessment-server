delete from checkpoint_score where facility_assessment_id = ?;
delete from facility_assessment_device where facility_assessment_id = ?;
delete from standard_score where facility_assessment_id = ?;
delete from area_of_concern_score where facility_assessment_id = ?;
delete from checklist_score where facility_assessment_id = ?;
delete from indicator where facility_assessment_id = ?;
delete from facility_assessment where id = ?;
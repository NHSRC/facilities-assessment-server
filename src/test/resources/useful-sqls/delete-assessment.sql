delete from facility_assessment where id = ?;
delete from checklist_score where facility_assessment_id = ?;
delete from indicator where facility_assessment_id = ?
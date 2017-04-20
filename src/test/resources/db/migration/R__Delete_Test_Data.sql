CREATE OR REPLACE FUNCTION delete_seed_test_data()
  RETURNS BOOLEAN AS $$
BEGIN
  DELETE FROM checkpoint_score;
  DELETE FROM facility_assessment;
  DELETE FROM facility;
  DELETE FROM facility_type;
  DELETE FROM district;
  DELETE FROM state;
  DELETE FROM "checkpoint";
  DELETE FROM measurable_element;
  DELETE FROM standard;
  DELETE FROM area_of_concern;
  DELETE FROM checklist;
  DELETE FROM department;
  DELETE FROM assessment_tool;
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
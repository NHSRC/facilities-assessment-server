CREATE OR REPLACE FUNCTION delete_seed_test_data()
  RETURNS BOOLEAN AS $$
BEGIN
  DELETE FROM indicator;
  DELETE FROM checkpoint_score;
  DELETE FROM facility_assessment_device;
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
  DELETE FROM indicator_definition;
  DELETE FROM department;
  DELETE FROM assessment_tool;
  DELETE FROM assessment_tool_mode;
  DELETE FROM assessment_type;
  DELETE FROM "role";

  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
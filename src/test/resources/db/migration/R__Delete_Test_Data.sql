CREATE OR REPLACE FUNCTION delete_seed_test_data()
  RETURNS BOOLEAN AS $$
BEGIN
  DELETE FROM facility;
  DELETE FROM facility_type;
  DELETE FROM district;
  DELETE FROM state;
  DELETE FROM measurable_element;
  DELETE FROM standard;
  DELETE FROM area_of_concern;
  DELETE FROM checkpoint;
  DELETE FROM checklist;
  DELETE FROM department;
  DELETE FROM assessment_tool;
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
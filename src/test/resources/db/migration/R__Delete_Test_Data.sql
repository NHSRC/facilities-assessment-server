CREATE OR REPLACE FUNCTION delete_seed_test_data()
  RETURNS BOOLEAN AS $$
BEGIN
  DELETE FROM facility;
  DELETE FROM facility_type;
  DELETE FROM district;
  DELETE FROM state;
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
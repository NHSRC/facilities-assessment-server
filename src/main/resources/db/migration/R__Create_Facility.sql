CREATE OR REPLACE FUNCTION create_facility(facility_name VARCHAR(255), district_id BIGINT, facility_type_id BIGINT)
  RETURNS BIGINT AS $$
DECLARE facility_id BIGINT;
BEGIN
  INSERT INTO facility (name, district_id, facility_type_id) VALUES (facility_name, district_id, facility_type_id)
  RETURNING id
    INTO facility_id;
  RAISE NOTICE 'Created Facility with facility id as: %, name: %', facility_id, facility_name;

  RETURN facility_id;
END;
$$ LANGUAGE plpgsql;
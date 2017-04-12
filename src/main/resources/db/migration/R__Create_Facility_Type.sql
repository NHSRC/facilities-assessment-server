CREATE OR REPLACE FUNCTION create_facility_type(facility_type_name VARCHAR(255), ft_uuid VARCHAR(1024))
  RETURNS BIGINT AS $$
DECLARE facility_type_id BIGINT;
BEGIN
  INSERT INTO facility_type (name, uuid) VALUES (facility_type_name, ft_uuid::UUID)
  RETURNING id
    INTO facility_type_id;
  RAISE NOTICE 'Created Facility Type with facility type id as: %, name: %', facility_type_id, facility_type_name;

  RETURN facility_type_id;
END;
$$ LANGUAGE plpgsql;
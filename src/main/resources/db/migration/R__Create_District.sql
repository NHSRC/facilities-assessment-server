CREATE OR REPLACE FUNCTION create_district(district_name VARCHAR(255), state_id BIGINT)
  RETURNS BIGINT AS $$
DECLARE district_id BIGINT;
BEGIN
  INSERT INTO district (name, state_id) VALUES (district_name, state_id)
  RETURNING id
    INTO district_id;
  RAISE NOTICE 'Created District with district id as: %, name: %', district_id, district_name;

  RETURN district_id;
END;
$$ LANGUAGE plpgsql;
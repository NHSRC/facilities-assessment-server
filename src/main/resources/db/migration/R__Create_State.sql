CREATE OR REPLACE FUNCTION create_state(state_name VARCHAR(255), st_uuid VARCHAR(1024))
  RETURNS BIGINT AS $$
DECLARE state_id BIGINT;
BEGIN
  INSERT INTO state (name, uuid) VALUES (state_name, st_uuid::UUID)
  RETURNING id
    INTO state_id;
  RAISE NOTICE 'Created State with state id as: %, name: %', state_id, state_name;

  RETURN state_id;
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION create_state(state_name VARCHAR(255))
  RETURNS BIGINT AS $$
DECLARE state_id BIGINT;
BEGIN
  INSERT INTO state (name) VALUES (state_name)
  RETURNING id
    INTO state_id;
  RAISE NOTICE 'Created State with state id as: %, name: %', state_id, state_name;

  RETURN state_id;
END;
$$ LANGUAGE plpgsql;
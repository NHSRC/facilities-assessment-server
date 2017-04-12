CREATE OR REPLACE FUNCTION create_standard(
  standard_name VARCHAR(1024),
  standard_ref  VARCHAR(255),
  aoc_id        BIGINT,
  std_uuid      VARCHAR(1024))
  RETURNS BIGINT AS $$
DECLARE standard_id BIGINT;
BEGIN
  INSERT INTO standard (name, reference, area_of_concern_id, uuid)
  VALUES (standard_name, standard_ref, aoc_id, std_uuid::UUID)
  RETURNING id
    INTO standard_id;

  RAISE NOTICE 'Created Standard with id as: %, name: %', standard_id, standard_name;

  RETURN standard_id;
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION create_area_of_concern(
  area_of_concern_name VARCHAR(1024),
  area_of_concern_ref  VARCHAR(255),
  aoc_uuid VARCHAR(1024))
  RETURNS BIGINT AS $$
DECLARE area_of_concern_id BIGINT;
BEGIN
  INSERT INTO area_of_concern (name, reference, uuid)
  VALUES (area_of_concern_name, area_of_concern_ref, aoc_uuid::UUID)
  RETURNING id
    INTO area_of_concern_id;

  RAISE NOTICE 'Created Area Of Concern with id as: %, name: %', area_of_concern_id, area_of_concern_name;

  RETURN area_of_concern_id;
END;
$$ LANGUAGE plpgsql;
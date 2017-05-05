CREATE OR REPLACE FUNCTION create_checklist_area_of_concern(
  aoc_uuid       VARCHAR(1024),
  checklist_uuid VARCHAR(255))
  RETURNS BIGINT AS $$
DECLARE   aoc_id                       BIGINT;
  DECLARE checklist_id                 BIGINT;
  DECLARE checklist_area_of_concern_id BIGINT;
BEGIN

  SELECT id
  INTO aoc_id
  FROM area_of_concern
  WHERE uuid = aoc_uuid::UUID;
  SELECT id
  INTO checklist_id
  FROM checklist
  WHERE uuid = checklist_uuid::UUID;
  INSERT INTO checklist_area_of_concern (area_of_concern_id, checklist_id) VALUES (aoc_id, checklist_id)
  RETURNING aoc_id
    INTO checklist_area_of_concern_id;

  RAISE NOTICE 'Created AOC Checklist Link with aoc id as: %, checklist id : %', aoc_id, checklist_id;

  RETURN checklist_area_of_concern_id;
END;
$$ LANGUAGE plpgsql;
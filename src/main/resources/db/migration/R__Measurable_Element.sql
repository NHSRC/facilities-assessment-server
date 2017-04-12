CREATE OR REPLACE FUNCTION create_measurable_element(
  measurable_element_name VARCHAR(255),
  measurable_element_ref  VARCHAR(255),
  std_id                  BIGINT,
  me_uuid                 VARCHAR(1024))
  RETURNS BIGINT AS $$
DECLARE measurable_element_id BIGINT;
BEGIN
  INSERT INTO measurable_element (name, reference, standard_id, uuid)
  VALUES (measurable_element_name, measurable_element_ref, std_id, me_uuid::UUID)
  RETURNING id
    INTO measurable_element_id;

  RAISE NOTICE 'Created measurable_element with id as: %, name: %', measurable_element_id, measurable_element_name;

  RETURN measurable_element_id;
END;
$$ LANGUAGE plpgsql;
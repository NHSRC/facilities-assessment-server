CREATE OR REPLACE FUNCTION create_department(department_name VARCHAR(255), d_uuid VARCHAR(1024))
  RETURNS BIGINT AS $$
DECLARE department_id BIGINT;
BEGIN
  INSERT INTO department (name, uuid)
  VALUES (department_name, d_uuid::UUID)
  RETURNING id
    INTO department_id;

  RAISE NOTICE 'Created Department with id as: %, name: %', department_id, department_name;

  RETURN department_id;
END;
$$ LANGUAGE plpgsql;
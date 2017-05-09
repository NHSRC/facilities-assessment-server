CREATE OR REPLACE FUNCTION create_assessment_tool(assessment_tool_name VARCHAR(255), at_uuid VARCHAR(1024), mode VARCHAR(50))
  RETURNS BIGINT AS $$
DECLARE assessment_tool_id BIGINT;
BEGIN
  INSERT INTO assessment_tool (name, uuid, mode) VALUES (assessment_tool_name, at_uuid::UUID, mode)
  RETURNING id
    INTO assessment_tool_id;
  RAISE NOTICE 'Created Assessment Tool with id as: %, name: %', assessment_tool_id, assessment_tool_name;

  RETURN assessment_tool_id;
END;
$$ LANGUAGE plpgsql;
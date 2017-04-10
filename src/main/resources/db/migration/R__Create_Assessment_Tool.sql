CREATE OR REPLACE FUNCTION create_assessment_tool(assessment_tool_name VARCHAR(255))
  RETURNS BIGINT AS $$
DECLARE assessment_tool_id BIGINT;
BEGIN
  INSERT INTO assessment_tool (name) VALUES (assessment_tool_name)
  RETURNING id
    INTO assessment_tool_id;
  RAISE NOTICE 'Created Assessment Tool with id as: %, name: %', assessment_tool_id, assessment_tool_name;

  RETURN assessment_tool_id;
END;
$$ LANGUAGE plpgsql;
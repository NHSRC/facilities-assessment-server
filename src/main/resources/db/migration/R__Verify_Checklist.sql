CREATE OR REPLACE FUNCTION verify_checklist(
  assessment_tool_name VARCHAR(255),
  checklist_name       VARCHAR(255)
)
  RETURNS VOID AS $$
DECLARE checklist_id BIGINT;
BEGIN
  SELECT checklist.id
  INTO checklist_id
  FROM checklist, assessment_tool
  WHERE checklist.assessment_tool_id = assessment_tool.id AND assessment_tool.name = assessment_tool_name AND checklist.name = checklist_name;

  IF checklist_id > 0
  THEN
  ELSE
    RAISE NOTICE 'Checklist: % and Assessment Tool: %s', checklist_name, assessment_tool_name;
  END IF;
END;
$$ LANGUAGE plpgsql;
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

  IF checklist_id IS NULL THEN
    RAISE NOTICE 'UNVERIFIED: Checklist=% and Assessment Tool=% and CHECKLIST_ID=%', checklist_name, assessment_tool_name, checklist_id;
  END IF;
END;
$$ LANGUAGE plpgsql;

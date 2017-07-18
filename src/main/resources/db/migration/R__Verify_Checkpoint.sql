CREATE OR REPLACE FUNCTION verify_checkpoint(
  assessment_tool_name VARCHAR(255),
  checklist_name       VARCHAR(255),
  checkpoint_name      VARCHAR(255)
)
  RETURNS VOID AS $$
DECLARE checkpoint_id BIGINT;
BEGIN
  SELECT checkpoint.id
  INTO checkpoint_id
  FROM checkpoint, checklist, assessment_tool
  WHERE checkpoint.checklist_id = checklist.id AND checklist.assessment_tool_id = assessment_tool.id AND assessment_tool.name = assessment_tool_name AND
        checklist.name = checklist_name AND checkpoint.name = checkpoint_name;

  IF checkpoint_id > 0 THEN
  ELSE
    RAISE NOTICE 'Checkpoint: %, was not found in Checklist: % and Assessment Tool: %s', checkpoint_name, checklist_name, assessment_tool_name;
  END IF;
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION create_checklist(checklist_name              VARCHAR(255),
                                           existing_department_id      BIGINT,
                                           existing_assessment_tool_id BIGINT,
                                            ch_uuid VARCHAR(1024))
  RETURNS BIGINT AS $$
DECLARE checklist_id BIGINT;
BEGIN
  INSERT INTO checklist (name, department_id, assessment_tool_id, uuid)
  VALUES (checklist_name, existing_department_id, existing_assessment_tool_id, ch_uuid::UUID)
  RETURNING id
    INTO checklist_id;

  RAISE NOTICE 'Created Checklist with Checklist id as: %, name: %', checklist_id, checklist_name;

  RETURN checklist_id;
END;
$$ LANGUAGE plpgsql;
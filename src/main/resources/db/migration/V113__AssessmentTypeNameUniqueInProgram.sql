ALTER TABLE assessment_type DROP CONSTRAINT assessment_type_name_key;
ALTER TABLE assessment_type ADD UNIQUE (assessment_tool_mode_id, name);
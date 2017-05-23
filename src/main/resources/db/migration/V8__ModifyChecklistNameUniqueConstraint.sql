ALTER TABLE checklist DROP CONSTRAINT "checklist_name_key";
ALTER TABLE checklist ADD UNIQUE (name, assessment_tool_id);
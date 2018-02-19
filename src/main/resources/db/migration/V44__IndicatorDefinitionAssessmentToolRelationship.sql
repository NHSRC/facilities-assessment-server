ALTER TABLE indicator_definition ADD COLUMN assessment_tool_id INT NOT NULL;
ALTER TABLE ONLY indicator_definition
  ADD CONSTRAINT indicator_definition_assessment_tool FOREIGN KEY (assessment_tool_id) REFERENCES assessment_tool (id);
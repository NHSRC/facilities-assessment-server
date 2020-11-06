alter table assessment_type add column assessment_tool_mode_id INT NOT NULL DEFAULT 2;
ALTER TABLE ONLY assessment_type
  ADD CONSTRAINT assessment_type_assessment_tool_mode FOREIGN KEY (assessment_tool_mode_id) REFERENCES assessment_tool_mode (id);

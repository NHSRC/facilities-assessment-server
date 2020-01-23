ALTER TABLE assessment_tool ADD COLUMN state_id INT NULL;
ALTER TABLE assessment_tool ADD CONSTRAINT checklist_state_id_fkey FOREIGN KEY (state_id) REFERENCES state (id);
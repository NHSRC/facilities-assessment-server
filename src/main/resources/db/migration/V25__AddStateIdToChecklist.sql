ALTER TABLE checklist ADD COLUMN state_id INT NULL;
ALTER TABLE checklist ADD CONSTRAINT checklist_state_id_fkey FOREIGN KEY (state_id) REFERENCES state (id);
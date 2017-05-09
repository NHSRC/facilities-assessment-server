ALTER TABLE checkpoint ALTER COLUMN state_id SET DEFAULT 1;
ALTER TABLE checkpoint ALTER COLUMN state_id SET NOT NULL;

ALTER TABLE assessment_tool ADD COLUMN mode VARCHAR(50) NULL;
UPDATE assessment_tool SET mode = 'nqas';
ALTER TABLE assessment_tool ADD COLUMN mode VARCHAR(50) NULL;
UPDATE assessment_tool SET mode = 'nqas';
ALTER TABLE facility_assessment ALTER COLUMN facility_id DROP NOT NULL;
ALTER TABLE facility_assessment ADD COLUMN facility_name VARCHAR(255) NULL;
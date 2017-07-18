ALTER TABLE facility_assessment ADD COLUMN series_name VARCHAR(50) NULL;
ALTER TABLE facility_assessment ADD UNIQUE (series_name, facility_id);
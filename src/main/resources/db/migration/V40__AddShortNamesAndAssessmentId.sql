ALTER TABLE state ADD COLUMN short_name VARCHAR(5) NULL UNIQUE;
ALTER TABLE assessment_tool_mode ADD COLUMN short_name VARCHAR(5) NULL UNIQUE;
ALTER TABLE assessment_type ADD COLUMN short_name VARCHAR(5) NULL UNIQUE;
ALTER TABLE facility_type ADD COLUMN short_name VARCHAR(5) NULL UNIQUE;
ALTER TABLE facility ADD COLUMN hmis_code VARCHAR(10) NULL;
ALTER TABLE facility_assessment ADD COLUMN assessment_code VARCHAR(100) NULL;
ALTER TABLE facility_assessment ADD COLUMN assessment_type_id INT NOT NULL DEFAULT 1;
ALTER TABLE facility_assessment ADD CONSTRAINT facility_assessment_assessment_type_id_fkey FOREIGN KEY (assessment_type_id) REFERENCES assessment_type (id);
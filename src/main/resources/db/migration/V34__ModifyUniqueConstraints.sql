ALTER TABLE facility_assessment DROP CONSTRAINT IF EXISTS facility_assessment_series_name_facility_id_key;
ALTER TABLE facility_assessment DROP CONSTRAINT IF EXISTS facility_assessment_series_name_facility_id_assessment_tool_key;
ALTER TABLE facility_assessment ADD UNIQUE (series_name, facility_id, assessment_tool_id);
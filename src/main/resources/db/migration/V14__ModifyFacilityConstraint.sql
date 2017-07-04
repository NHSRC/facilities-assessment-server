ALTER TABLE facility DROP CONSTRAINT facility_facility_type_id_name_key;
ALTER TABLE facility ADD UNIQUE (district_id, facility_type_id, name);
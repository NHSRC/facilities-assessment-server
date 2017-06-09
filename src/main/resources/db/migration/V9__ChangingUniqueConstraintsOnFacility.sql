ALTER TABLE facility
  DROP CONSTRAINT facility_name_key;

ALTER TABLE facility
  ADD UNIQUE (facility_type_id, name);

ALTER TABLE district
  ADD UNIQUE (name);
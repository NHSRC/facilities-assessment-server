ALTER TABLE district DROP CONSTRAINT district_name_key;
ALTER TABLE district ADD UNIQUE (state_id, name);
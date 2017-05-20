ALTER TABLE standard ADD UNIQUE (reference, area_of_concern_id);
ALTER TABLE measurable_element ADD UNIQUE (reference, standard_id);
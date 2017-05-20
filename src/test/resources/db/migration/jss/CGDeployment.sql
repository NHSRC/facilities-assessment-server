CREATE OR REPLACE FUNCTION jss_cg_create_reference_data()
  RETURNS BOOLEAN AS $$
DECLARE   state_id                 BIGINT;
  DECLARE district_id              BIGINT;
  DECLARE district_hospital_id     BIGINT;
  DECLARE facility_id              BIGINT;
BEGIN
  state_id = create_state('Chhattisgarh', '7e58bbc2-bcd6-43fe-bb27-c326ebf33ba0');
  district_id = create_district('Mungeli', state_id, 'cb59381a-c8b9-413c-940d-898475eb3c73');
  facility_id = create_facility('Mungeli District Hospital', district_id, district_hospital_id, '0a56a411-e859-4f8f-b281-6c6f84d43fa9');
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

select jss_cg_create_reference_data();
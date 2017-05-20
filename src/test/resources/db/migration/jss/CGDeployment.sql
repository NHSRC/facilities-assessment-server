CREATE OR REPLACE FUNCTION jss_cg_create_reference_data()
  RETURNS BOOLEAN AS $$
DECLARE   state_id                 BIGINT;
  DECLARE district_id              BIGINT;
  DECLARE district_hospital_id     BIGINT;
  DECLARE facility_id              BIGINT;
BEGIN
  state_id = create_state('Chhattisgarh', '7e58bbc2-bcd6-43fe-bb27-c326ebf33ba0');
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

select jss_cg_create_reference_data();
CREATE OR REPLACE FUNCTION jss_mp_create_reference_data()
  RETURNS BOOLEAN AS $$
DECLARE   state_id                 BIGINT;
  DECLARE district_id              BIGINT;
  DECLARE district_hospital_id     BIGINT;
  DECLARE facility_id              BIGINT;
BEGIN
  state_id = create_state('Chhattisgarh', '7e58bbc2-bcd6-43fe-bb27-c326ebf33ba0');
  district_id = create_district('Bilaspur', state_id, '1fc57f3d-20ee-4079-a1de-c6289fe5a73e');
  facility_id = create_facility('Jan Swasthya Sahyog (JSS)', district_id, district_hospital_id, '77cceb53-7d71-456c-a9ee-c870774707ad');
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

select jss_mp_create_reference_data();
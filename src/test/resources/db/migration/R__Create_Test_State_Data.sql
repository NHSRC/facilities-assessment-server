CREATE OR REPLACE FUNCTION create_seed_test_data()
  RETURNS BOOLEAN AS $$
DECLARE   state_id                 BIGINT;
  DECLARE district_id              BIGINT;
  DECLARE district_hospital_id     BIGINT;
  DECLARE community_hospital_id    BIGINT;
  DECLARE primary_health_center_id BIGINT;
  DECLARE facility_id              BIGINT;
BEGIN
  district_hospital_id = create_facility_type('District Hospital', '77cceb53-7d71-456c-a9ee-c870774707ad');
  primary_health_center_id = create_facility_type('Primary Health Center', 'c1837c18-5b63-4e3f-8e56-cb5d9bdf2e86');
  community_hospital_id = create_facility_type('Community Hospital', 'be22ffe8-ce18-4aac-a50d-fa1ee9a93251');
  state_id = create_state('Chhattisgarh', '7e58bbc2-bcd6-43fe-bb27-c326ebf33ba0');
  district_id = create_district('Bilaspur', state_id, '1fc57f3d-20ee-4079-a1de-c6289fe5a73e');
  facility_id = create_facility('Jan Swasthya Sahyog (JSS)', district_id, district_hospital_id,
                                '77cceb53-7d71-456c-a9ee-c870774707ad');
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
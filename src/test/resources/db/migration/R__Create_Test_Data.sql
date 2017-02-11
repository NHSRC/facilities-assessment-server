CREATE OR REPLACE FUNCTION create_seed_test_data()
  RETURNS BOOLEAN AS $$
DECLARE   state_id                 BIGINT;
  DECLARE district_id              BIGINT;
  DECLARE district_hospital_id     BIGINT;
  DECLARE community_hospital_id    BIGINT;
  DECLARE primary_health_center_id BIGINT;
  DECLARE facility_id              BIGINT;
BEGIN
  district_hospital_id = create_facility_type('District Hospital');
  community_hospital_id = create_facility_type('Community Hospital');
  primary_health_center_id = create_facility_type('Primary Health Center');
  state_id = create_state('Punjab');
  district_id = create_district('Jalandhar', state_id);
  facility_id = create_facility('National Kidney Hospitals', district_id, district_hospital_id);
  facility_id = create_facility('Kapil Hospital', district_id, community_hospital_id);
  facility_id = create_facility('Balaji Medicare Hospital', district_id, primary_health_center_id);
  district_id = create_district('Amritsar', state_id);
  facility_id = create_facility('Fortis Hospital', district_id, district_hospital_id);
  facility_id = create_facility('ESI Corp', district_id, community_hospital_id);
  facility_id = create_facility('Shoor Hospital', district_id, primary_health_center_id);
  state_id = create_state('Himachal Pradesh');
  district_id = create_district('Shimla', state_id);
  facility_id = create_facility('MY Hospital', district_id, district_hospital_id);
  facility_id = create_facility('Ramesh Medicare', district_id, primary_health_center_id);
  district_id = create_district('Kullu', state_id);
  facility_id = create_facility('Suresh Hospital', district_id, district_hospital_id);
  facility_id = create_facility('Good Care', district_id, community_hospital_id);
  facility_id = create_facility('Free Healthcare', district_id, primary_health_center_id);
  state_id = create_state('Karnataka');
  district_id = create_district('Bangalore', state_id);
  facility_id = create_facility('Manipal Hospital', district_id, district_hospital_id);
  facility_id = create_facility('ESI Hospital', district_id, community_hospital_id);
  facility_id = create_facility('Narayana Hospital', district_id, primary_health_center_id);
  district_id = create_district('Hubli', state_id);
  facility_id = create_facility('KIMS Hospital', district_id, district_hospital_id);
  facility_id = create_facility('NIMHANS', district_id, community_hospital_id);
  facility_id = create_facility('Iyengar Hospital', district_id, primary_health_center_id);
  state_id = create_state('Andhra Pradesh');
  district_id = create_district('Hyderabad', state_id);
  facility_id = create_facility('Good Medicare Hospital', district_id, district_hospital_id);
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
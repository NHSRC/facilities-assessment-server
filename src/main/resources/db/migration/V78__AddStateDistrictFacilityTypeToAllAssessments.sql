alter table facility_assessment
  add column state_id int null,
  add column district_id int null,
  add column facility_type_id int null;

update facility_assessment set district_id = facility.district_id
  from facility
  where facility_assessment.facility_id = facility.id and facility_assessment.facility_id is not null;

update facility_assessment set facility_type_id = facility.facility_type_id
  from facility
  where facility_assessment.facility_id = facility.id and facility_assessment.facility_id is not null;

update facility_assessment set state_id = d2.state_id
  from facility join district d2 on facility.district_id = d2.id
  where facility_assessment.facility_id = facility.id and facility_assessment.facility_id is not null;

delete from nin_sync_details where type = 'FacilityMetadata';
update nin_sync_details set offset_successfully_processed = 0 where type = 'Facility';
alter table nin_sync_details add column date_processed_upto varchar(50) not null default '2021-02-24';
alter table nin_sync_details add column has_more_for_date bool not null default false;
alter table facility add unique(registry_unique_id,inactive);
alter table facility drop constraint facility_district_id_facility_type_id_name_key;

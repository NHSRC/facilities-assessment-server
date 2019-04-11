update facility_assessment set inactive = false where inactive is null;
alter table facility_assessment alter column inactive set not null;
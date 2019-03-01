delete from role_privilege where privilege_id in (select id from privilege where name in ('Privilege_Read', 'Checklist_Metadata_Read', 'Checklist_Read', 'Facility_Read', 'Facility_Metadata_Read'));

delete from privilege where name in ('Privilege_Read', 'Checklist_Metadata_Read', 'Checklist_Read', 'Facility_Read', 'Facility_Metadata_Read');
update privilege set name = 'Checklist_Metadata_Write' where name = 'Checklist_MetadataWrite';
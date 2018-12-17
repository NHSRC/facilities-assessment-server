delete from checklist_area_of_concern a
 using checklist_area_of_concern b
 where a.id < b.id and a.area_of_concern_id = b.area_of_concern_id and a.checklist_id = b.checklist_id;
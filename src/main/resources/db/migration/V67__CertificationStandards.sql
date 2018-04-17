CREATE TABLE certification_standard (
  id                 SERIAL PRIMARY KEY,
  standard_id        int not null,
  assessment_tool_id int not null
);
ALTER TABLE ONLY certification_standard
  ADD CONSTRAINT certification_standard_standard FOREIGN KEY (standard_id) REFERENCES standard (id);
ALTER TABLE ONLY certification_standard
  ADD CONSTRAINT certification_standard_assessment_tool FOREIGN KEY (assessment_tool_id) REFERENCES assessment_tool (id);


insert into certification_standard (standard_id, assessment_tool_id) select distinct standard.id, a2.id
   from standard
     inner join area_of_concern a on standard.area_of_concern_id = a.id
     inner join checklist_area_of_concern c3 on a.id = c3.area_of_concern_id
     inner join checklist c2 on c3.checklist_id = c2.id
     inner join assessment_tool a2 on c2.assessment_tool_id = a2.id
   where a2.name = 'District Hospital (DH)' and standard.reference in ('A2', 'B5', 'D10');

insert into certification_standard (standard_id, assessment_tool_id) select distinct standard.id, a2.id
   from standard
     inner join area_of_concern a on standard.area_of_concern_id = a.id
     inner join checklist_area_of_concern c3 on a.id = c3.area_of_concern_id
     inner join checklist c2 on c3.checklist_id = c2.id
     inner join assessment_tool a2 on c2.assessment_tool_id = a2.id
   where a2.name = 'Primary Health Center (PHC)' and standard.reference in ('A2', 'B5', 'D8');

insert into certification_standard (standard_id, assessment_tool_id) select distinct standard.id, a2.id
   from standard
     inner join area_of_concern a on standard.area_of_concern_id = a.id
     inner join checklist_area_of_concern c3 on a.id = c3.area_of_concern_id
     inner join checklist c2 on c3.checklist_id = c2.id
     inner join assessment_tool a2 on c2.assessment_tool_id = a2.id
   where a2.name = 'Community Health Center (CHC)' and standard.reference in ('A2', 'B5', 'F6');

insert into certification_standard (standard_id, assessment_tool_id) select distinct standard.id, a2.id
   from standard
     inner join area_of_concern a on standard.area_of_concern_id = a.id
     inner join checklist_area_of_concern c3 on a.id = c3.area_of_concern_id
     inner join checklist c2 on c3.checklist_id = c2.id
     inner join assessment_tool a2 on c2.assessment_tool_id = a2.id
   where a2.name = 'Urban Primary Health Center (UPHC)' and standard.reference in ('A2', 'B5', 'F6');

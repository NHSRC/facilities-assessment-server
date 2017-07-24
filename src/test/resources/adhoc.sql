SELECT assessment_tool_id, name, id
FROM checklist ORDER BY assessment_tool_id, name;

select verify_checkpoint('Community Health Center (CHC)', 'Outpatient Department', 'Availability departmental signage''s');

select verify_checklist('District Hospital (DH)', 'Labour Room');

SELECT checklist.id
FROM checklist, assessment_tool
WHERE checklist.assessment_tool_id = assessment_tool.id AND assessment_tool.name = 'District Hospital (DH)' AND checklist.name = 'Labour Room';

SELECT checkpoint.id
FROM checkpoint, checklist, assessment_tool
WHERE checkpoint.checklist_id = checklist.id AND checklist.assessment_tool_id = assessment_tool.id AND assessment_tool.name = 'District Hospital (DH)' AND
      checklist.name = 'Blood Bank' AND checkpoint.name = 'Blood bank services available 24X7';

SELECT * FROM checkpoint WHERE name = 'Blood bank services available 24X7';

SELECT * from checklist WHERE name = 'Blood Bank';

SELECT * from checkpoint_score WHERE checklist_id = 20;
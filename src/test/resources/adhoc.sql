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


set SEARCH_PATH TO mp;

SELECT facility.id, facility.name, state.name, district.name from facility, district, state where district.state_id = state.id AND facility.district_id = district.id;
SELECT f.name, d.id, f.facility_type_id from mp.facility f, public.district d WHERE f.district_id = d.self_id;
SELECT f.name, f.facility_type_id from mp.facility f;
SELECT * from public.district;
SELECT name, id from mp.state;
SELECT d.name, s.id from mp.district d, public.state s WHERE d.state_id = s.self_id;

SELECT state_id, assessment_tool_id, name from public.checklist order by state_id, assessment_tool_id, name;
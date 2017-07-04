select aoc.reference as AOC, s.reference as Standard, me.reference as ME
from area_of_concern aoc, standard s, measurable_element me
where s.area_of_concern_id = aoc.id and me.standard_id = s.id and aoc.assessment_tool_id = (select id from assessment_tool where name = 'Community Hospital (CH)')
order by AOC, Standard, ME;



SELECT DISTINCT COUNT(*) AS Count, last_modified_date FROM checkpoint GROUP BY last_modified_date order by Count desc;

select DISTINCT checklist.name from checkpoint_score, checklist where checkpoint_score.checklist_id = checklist.id;

select cl.assessment_tool_id , cl.name ChecklistName, count(cs.id) as NumScores from checkpoint_score cs, checklist cl where cs.checklist_id = cl.id GROUP BY cl.assessment_tool_id, cl.name ORDER BY cl.assessment_tool_id;

-- After importing checklist
-- To find out whether all the checklists have checkpoints in them. Match it with number of checklists (0s are not shown)
select cl.id, cl.name ChecklistName, count(c.id) as NumCheckpoints from checkpoint c inner join checklist cl on c.checklist_id = cl.id GROUP BY cl.name, cl.id order by cl.id;
-- Verify the hierarchy visually by running the following query
select at.mode as AssessmentMode, at.name as AssessmentTool, c.name as Checklist, aoc.reference as AOC, s.reference as Standard, me.reference as ME, substr(cp.name, 0, 20) as Checkpoint
from checklist c, area_of_concern aoc, checklist_area_of_concern caoc, standard s, measurable_element me, checkpoint cp, assessment_tool at
where cp.checklist_id = c.id and caoc.checklist_id = c.id and aoc.id = caoc.area_of_concern_id and s.area_of_concern_id = aoc.id and me.standard_id = s.id and cp.measurable_element_id = me.id and c.assessment_tool_id = at.id
order by AssessmentMode, AssessmentTool, Checklist, AOC, Standard, ME, Checkpoint;
-- Verify the hierarchy visually by running the following query for a particular case
select c.name as Checklist, aoc.reference as AOC, s.reference as Standard, me.reference as ME, cp.name as Checkpoint
from checklist c, area_of_concern aoc, checklist_area_of_concern caoc, standard s, measurable_element me, checkpoint cp
where cp.checklist_id = c.id and caoc.checklist_id = c.id and aoc.id = caoc.area_of_concern_id and s.area_of_concern_id = aoc.id and me.standard_id = s.id and cp.measurable_element_id = me.id
  and c.name = 'Blood Storage Unit'
order by Checklist, AOC, Standard, ME, Checkpoint;
-- Check any cases where me and standard are under the wrong parent
SELECT at.mode as AssessmentMode, at.name as AssessmentTool, c.name as Checklist, me.id ME_ID, me.reference ME, s.reference STD from measurable_element me, standard s, area_of_concern aoc, checklist c, assessment_tool at, checklist_area_of_concern caoc
WHERE me.standard_id = s.id and substr(me.reference, 1, 1) != substr(s.reference, 1, 1) and s.area_of_concern_id = aoc.id and c.assessment_tool_id = at.id and c.id = caoc.checklist_id and aoc.id = caoc.area_of_concern_id;
-- Any checkpoints which are from garbage row
SELECT at.mode as AssessmentMode, at.name as AssessmentTool, c.name as Checklist, me.id ME_ID, me.reference ME, cp.name as Checkpoint, cp.id as CP_ID from measurable_element me, checklist c, assessment_tool at, checkpoint cp
WHERE c.assessment_tool_id = at.id and cp.checklist_id = c.id and cp.measurable_element_id = me.id AND char_length(cp.name) < 10;

-- Facilities
select s.name as STATE, d.name as DISTRICT, f.id as ID, f.name as NAME, ft.name as TYPE from facility f, facility_type ft, district d, state s
where f.facility_type_id = ft.id and f.district_id = d.id and d.state_id = s.id;

-- COUNT OF STANDARDS SCORE FILLED
select s.uuid, count(s.id) from checkpoint cp, checkpoint_score cps, facility_assessment fa, standard s, measurable_element me where cps.checkpoint_id = cp.id and cps.facility_assessment_id = fa.id and cp.measurable_element_id = me.id and me.standard_id = s.id and fa.id = 1 GROUP BY s.id;
-- TOTAL # OF STANDARDS
select s.uuid, count(s.id) from checkpoint cp, standard s, measurable_element me, assessment_tool at, checklist cl where cp.measurable_element_id = me.id and me.standard_id = s.id and cp.checklist_id = cl.id and cl.assessment_tool_id = at.id and at.id = 1 GROUP BY s.id;

-- COUNT OF AOC SCORE FILLED
select aoc.uuid, count(aoc.id) from checkpoint cp, checkpoint_score cps, facility_assessment fa, standard s, measurable_element me, area_of_concern aoc
where cps.checkpoint_id = cp.id and cps.facility_assessment_id = fa.id and cp.measurable_element_id = me.id and me.standard_id = s.id and s.area_of_concern_id = aoc.id and fa.id = 1
GROUP BY aoc.id;
select aoc.uuid, cl.uuid, count(aoc.id) from checkpoint cp, area_of_concern aoc, standard s, measurable_element me, assessment_tool at, checklist cl where cp.measurable_element_id = me.id and me.standard_id = s.id and cp.checklist_id = cl.id and cl.assessment_tool_id = at.id and aoc.id = s.area_of_concern_id and at.id = 1 GROUP BY (cl.id, aoc.id);

-- COUNT OF CHECKLIST SCORE FILLED
select cl.uuid, count(cl.id) from checkpoint_score cps, facility_assessment fa, checklist cl where cps.facility_assessment_id = fa.id and fa.id = 1 and cps.checklist_id = cl.id GROUP BY cl.id;
select cl.uuid, count(cl.id) from checkpoint cp, assessment_tool at, checklist cl where cp.checklist_id = cl.id and cl.assessment_tool_id = at.id and at.id = 1 GROUP BY cl.id;

-- Kayakalp
SELECT cp.means_of_verification from checkpoint cp, checklist cl WHERE cp.checklist_id = cl.id and cl.name = 'Kayakalp';
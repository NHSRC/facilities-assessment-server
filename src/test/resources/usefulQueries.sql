select aoc.reference as AOC, s.reference as Standard, me.reference as ME
from area_of_concern aoc, standard s, measurable_element me
where s.area_of_concern_id = aoc.id and me.standard_id = s.id and aoc.assessment_tool_id = (select id from assessment_tool where name = 'Community Hospital (CH)')
order by AOC, Standard, ME;



SELECT DISTINCT COUNT(*) AS Count, last_modified_date FROM checkpoint GROUP BY last_modified_date order by Count desc;

select DISTINCT checklist.name from checkpoint_score, checklist where checkpoint_score.checklist_id = checklist.id;

select cl.name ChecklistName, count(cs.id) as NumScores from checkpoint_score cs inner join checklist cl on cs.checklist_id = cl.id GROUP BY cl.name;

-- After importing checklist
-- To find out whether all the checklists have checkpoints in them. Match it with number of checklists (0s are not shown)
select cl.id, cl.name ChecklistName, count(c.id) as NumCheckpoints from checkpoint c inner join checklist cl on c.checklist_id = cl.id GROUP BY cl.name, cl.id order by cl.id;
-- Verify the hierarchy visually by running the following query
select c.name as Checklist, aoc.reference as AOC, s.reference as Standard, me.reference as ME, cp.name as Checkpoint
from checklist c, area_of_concern aoc, checklist_area_of_concern caoc, standard s, measurable_element me, checkpoint cp
where cp.checklist_id = c.id and caoc.checklist_id = c.id and aoc.id = caoc.area_of_concern_id and s.area_of_concern_id = aoc.id and me.standard_id = s.id and cp.measurable_element_id = me.id
  and c.name = 'Blood Storage Unit'
order by Checklist, AOC, Standard, ME, Checkpoint;
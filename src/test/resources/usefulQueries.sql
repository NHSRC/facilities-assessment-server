select aoc.reference as AOC, s.reference as Standard, me.reference as ME
from area_of_concern aoc, standard s, measurable_element me
where s.area_of_concern_id = aoc.id and me.standard_id = s.id and aoc.assessment_tool_id = (select id from assessment_tool where name = 'Community Hospital (CH)')
order by AOC, Standard, ME;

select c.name as Checklist, aoc.reference as AOC, s.reference as Standard, me.reference as ME, cp.name as Checkpoint
from checklist c, area_of_concern aoc, checklist_area_of_concern caoc, standard s, measurable_element me, checkpoint cp
where cp.checklist_id = c.id and caoc.checklist_id = c.id and aoc.id = caoc.area_of_concern_id and s.area_of_concern_id = aoc.id and me.standard_id = s.id and cp.measurable_element_id = me.id
order by Checklist, AOC, Standard, ME, Checkpoint;

select c.name as Checklist, aoc.reference as AOC, s.reference as Standard, me.reference as ME, me.name
from checklist c, area_of_concern aoc, checklist_area_of_concern caoc, standard s, measurable_element me
where caoc.checklist_id = c.id and aoc.id = caoc.area_of_concern_id and s.area_of_concern_id = aoc.id and me.standard_id = s.id and me.reference = 'BC5.7'
order by Checklist, AOC, Standard, ME;

SELECT DISTINCT COUNT(*) AS Count, last_modified_date FROM checkpoint GROUP BY last_modified_date order by Count desc;
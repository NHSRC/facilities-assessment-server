SELECT
  cl.name    AS "Checklist Name",
  d.name     AS "Department",
  aoc.name   AS "Area Of Concern",
  s.name     AS "Standard",
  me.name    AS "Measurable Element",
  c.name     AS "Checkpoint",
  cs.score   AS "Score",
  cs.remarks AS "Remarks",
  max(fa.start_date) AS "Assessment Start Date"
FROM checkpoint_score cs
  INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
  LEFT OUTER JOIN measurable_element me ON me.id = c.measurable_element_id
  LEFT OUTER JOIN standard s ON s.id = me.standard_id
  LEFT OUTER JOIN area_of_concern aoc ON aoc.id = s.area_of_concern_id
  LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id
  LEFT OUTER JOIN department d ON d.id = cl.department_id
  LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
  LEFT OUTER JOIN facility f ON f.id = fa.facility_id
WHERE f.name = {{facility_name}}
[[and cl.name={{checklist_name}}]]
[[and d.name={{department_name}}]]
[[and aoc.name={{aoc_name}}]]
[[and s.name={{standard_name}}]]
[[and cs.score>={{min_score}}]]
[[and cs.score<={{max_score}}]]
GROUP BY cs.id, cl.name, d.name, aoc.name, S.name, me.name, C.name, cs.score, cs.remarks
ORDER BY cs.id, cl.name, d.name;
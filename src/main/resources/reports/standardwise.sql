SELECT
  sw.ref as "Reference",
  sw.standard_name       AS "Standard",
  sw.standard_score AS "Score"
FROM
  (SELECT
     s.reference as ref,
     s.name                                                                          AS standard_name,
     (sum(cs.score) :: FLOAT / (2 * count(cs.score) :: FLOAT) :: FLOAT * 100 :: FLOAT) AS standard_score,
     max(fa.start_date)
   FROM checkpoint_score cs
     INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
     LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id
     LEFT OUTER JOIN department d ON cl.department_id = d.id
     LEFT OUTER JOIN measurable_element me ON me.id = c.measurable_element_id
     LEFT OUTER JOIN standard s ON s.id = me.standard_id
     LEFT OUTER JOIN area_of_concern aoc ON aoc.id = s.area_of_concern_id
     LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
     LEFT OUTER JOIN facility f ON fa.facility_id = f.id
     WHERE f.name = {{facility_name}} [[and d.name = {{department_name}}]]
   GROUP BY s.name, s.reference ORDER BY s.reference) AS sw
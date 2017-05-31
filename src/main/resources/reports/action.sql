SELECT
  row_number()
  OVER (),
  to_char(max(fa.start_date), 'DD Mon YYYY') AS "Assessment Date",
  aoc.reference         "Area of Concern Reference",
  aoc.name              "Area of Concern",
  s.reference           "Standard Reference",
  s.name                "Standard",
  me.reference          "Measurable Element Reference",
  me.name               "Measurable Element",
  c.name                "Checkpoint",
  cs.score              "Score",
  cs.remarks            "Remarks",
  ''                    "Action",
  ''                    "Primary Responsibility (Staff+Dept I/C)",
  ''                    "Secondary Responsibility (QA team memb / CS/CMHO)",
  ''                    "Timeline"
FROM checkpoint_score cs
  INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
  INNER JOIN measurable_element me ON c.measurable_element_id = me.id
  INNER JOIN standard s ON me.standard_id = s.id
  INNER JOIN area_of_concern aoc ON aoc.id = s.area_of_concern_id
  INNER JOIN checklist ch ON c.checklist_id = ch.id
  INNER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
  INNER JOIN facility f on fa.facility_id = f.id
  WHERE f.name = {{facility_name}} [[and ch.name = {{department_name}}]]
GROUP BY aoc.reference, aoc.name, s.reference, s.name, me.reference, me.name, c.name, c.id, s.id, me.id, cs.score, cs.remarks
ORDER BY aoc.reference,
  s.id, me.id, c.id
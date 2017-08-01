-- ASSESSMENT TOOLS AND CHECKLIST
SELECT at.name, cl.name from assessment_tool at, checklist cl WHERE cl.assessment_tool_id = at.id ORDER BY at.name, cl.name;

-- TOTAL SCORE OF FACILITY
SELECT
  facility_district.district,
  facility_district.facility,
  facility_score.score
FROM
  (SELECT
     facility.id                                                                                   AS facility_id,
     round((sum(cs.score) :: FLOAT / (2 * count(cs.score) :: FLOAT) :: FLOAT * 100) :: NUMERIC, 1) AS score
   FROM checkpoint_score cs
     INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
     LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id
     LEFT OUTER JOIN department d ON d.id = cl.department_id
     LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
     LEFT OUTER JOIN facility ON fa.facility_id = facility.id
     LEFT OUTER JOIN district ON facility.district_id = district.id
   GROUP BY facility.id
   ORDER BY facility.id
  ) AS facility_score,
  (SELECT
     facility.id   id,
     district.name district,
     facility.name facility
   FROM facility, district
   WHERE facility.district_id = district.id
   ORDER BY facility.id) AS facility_district
WHERE facility_district.id = facility_score.facility_id;


SELECT *
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'facilities_assessment_cg'
      AND pid <> pg_backend_pid();

SELECT DISTINCT
  COUNT(*) AS Count,
  last_modified_date
FROM checkpoint
GROUP BY last_modified_date
ORDER BY Count DESC;


SELECT
  cl.assessment_tool_id,
  cl.name         ChecklistName,
  count(cs.id) AS NumScores
FROM checkpoint_score cs, checklist cl
WHERE cs.checklist_id = cl.id
GROUP BY cl.assessment_tool_id, cl.name
ORDER BY cl.assessment_tool_id;

-- After importing checklist
-- To find out whether all the checklists have checkpoints in them. Match it with number of checklists (0s are not shown)
SELECT
  cl.id,
  cl.name        ChecklistName,
  count(c.id) AS NumCheckpoints
FROM checkpoint c INNER JOIN checklist cl ON c.checklist_id = cl.id
GROUP BY cl.name, cl.id
ORDER BY cl.id;

-- Verify the hierarchy visually by running the following query
SELECT
  at.mode                AS AssessmentMode,
  at.name                AS AssessmentTool,
  c.name                 AS Checklist,
  aoc.reference          AS AOC,
  s.reference            AS Standard,
  me.reference           AS ME,
  substr(cp.name, 0, 20) AS Checkpoint
FROM checklist c, area_of_concern aoc, checklist_area_of_concern caoc, standard s, measurable_element me, checkpoint cp, assessment_tool at
WHERE cp.checklist_id = c.id AND caoc.checklist_id = c.id AND aoc.id = caoc.area_of_concern_id AND s.area_of_concern_id = aoc.id AND me.standard_id = s.id AND
      cp.measurable_element_id = me.id AND c.assessment_tool_id = at.id AND at.name = 'Dakshata'
ORDER BY AssessmentMode, AssessmentTool, Checklist, AOC, Standard, ME, Checkpoint;

-- Verify the hierarchy visually by running the following query for a particular case
SELECT
  c.name        AS Checklist,
  aoc.reference AS AOC,
  s.reference   AS Standard,
  me.reference  AS ME,
  cp.name       AS Checkpoint
FROM checklist c, area_of_concern aoc, checklist_area_of_concern caoc, standard s, measurable_element me, checkpoint cp
WHERE cp.checklist_id = c.id AND caoc.checklist_id = c.id AND aoc.id = caoc.area_of_concern_id AND s.area_of_concern_id = aoc.id AND me.standard_id = s.id AND
      cp.measurable_element_id = me.id
      AND c.name = 'Blood Storage Unit'
ORDER BY Checklist, AOC, Standard, ME, Checkpoint;

-- Check any cases where me and standard are under the wrong parent
SELECT
  at.mode AS   AssessmentMode,
  at.name AS   AssessmentTool,
  c.name  AS   Checklist,
  me.id        ME_ID,
  me.reference ME,
  s.reference  STD
FROM measurable_element me, standard s, area_of_concern aoc, checklist c, assessment_tool at, checklist_area_of_concern caoc
WHERE me.standard_id = s.id AND substr(me.reference, 1, 1) != substr(s.reference, 1, 1) AND s.area_of_concern_id = aoc.id AND c.assessment_tool_id = at.id AND
      c.id = caoc.checklist_id AND aoc.id = caoc.area_of_concern_id;
-- Any checkpoints which are from garbage row
SELECT
  at.mode AS   AssessmentMode,
  at.name AS   AssessmentTool,
  c.name  AS   Checklist,
  me.id        ME_ID,
  me.reference ME,
  cp.name AS   Checkpoint,
  cp.id   AS   CP_ID
FROM measurable_element me, checklist c, assessment_tool at, checkpoint cp
WHERE c.assessment_tool_id = at.id AND cp.checklist_id = c.id AND cp.measurable_element_id = me.id AND char_length(cp.name) < 10;

-- Facilities
SELECT
  s.name  AS STATE,
  d.name  AS DISTRICT,
  f.id    AS ID,
  f.name  AS NAME,
  ft.name AS TYPE
FROM facility f, facility_type ft, district d, state s
WHERE f.facility_type_id = ft.id AND f.district_id = d.id AND d.state_id = s.id;

-- COUNT OF STANDARDS SCORE FILLED
SELECT
  s.uuid,
  count(s.id)
FROM checkpoint cp, checkpoint_score cps, facility_assessment fa, standard s, measurable_element me
WHERE cps.checkpoint_id = cp.id AND cps.facility_assessment_id = fa.id AND cp.measurable_element_id = me.id AND me.standard_id = s.id AND fa.id = 1
GROUP BY s.id;
-- TOTAL # OF STANDARDS
SELECT
  s.uuid,
  count(s.id)
FROM checkpoint cp, standard s, measurable_element me, assessment_tool at, checklist cl
WHERE cp.measurable_element_id = me.id AND me.standard_id = s.id AND cp.checklist_id = cl.id AND cl.assessment_tool_id = at.id AND at.id = 1
GROUP BY s.id;

-- COUNT OF AOC SCORE FILLED
SELECT
  aoc.uuid,
  count(aoc.id)
FROM checkpoint cp, checkpoint_score cps, facility_assessment fa, standard s, measurable_element me, area_of_concern aoc
WHERE
  cps.checkpoint_id = cp.id AND cps.facility_assessment_id = fa.id AND cp.measurable_element_id = me.id AND me.standard_id = s.id AND s.area_of_concern_id = aoc.id AND
  fa.id = 1
GROUP BY aoc.id;
SELECT
  aoc.uuid,
  cl.uuid,
  count(aoc.id)
FROM checkpoint cp, area_of_concern aoc, standard s, measurable_element me, assessment_tool at, checklist cl
WHERE
  cp.measurable_element_id = me.id AND me.standard_id = s.id AND cp.checklist_id = cl.id AND cl.assessment_tool_id = at.id AND aoc.id = s.area_of_concern_id AND at.id = 1
GROUP BY (cl.id, aoc.id);

-- COUNT OF CHECKLIST SCORE FILLED
SELECT
  cl.name,
  count(cl.id)
FROM checkpoint_score cps, facility_assessment fa, checklist cl
WHERE cps.facility_assessment_id = fa.id AND fa.id = 4 AND cps.checklist_id = cl.id
GROUP BY cl.id;
SELECT
  cl.uuid,
  count(cl.id)
FROM checkpoint cp, assessment_tool at, checklist cl, facility_assessment fa
WHERE cp.checklist_id = cl.id AND cl.assessment_tool_id = at.id AND at.id = 1
GROUP BY cl.id;

-- Assessments Filled Summary
SELECT DISTINCT (fa.id, f.name, cl.name)
FROM facility_assessment fa, facility f, checklist cl, checkpoint_score cps
WHERE cps.facility_assessment_id = fa.id AND cps.checklist_id = cl.id AND fa.facility_id = f.id;

-- Kayakalp
SELECT cp.means_of_verification
FROM checkpoint cp, checklist cl
WHERE cp.checklist_id = cl.id AND cl.name = 'Kayakalp';

-- CROSS TAB EXAMPLE
SELECT *
FROM crosstab(
       'SELECT
         d.name                                         AS Department,
         aoc.name                                       AS AreaOfConcern,
         round((sum(cs.score) :: FLOAT / (2 * count(cs.score) :: FLOAT) :: FLOAT * 100) :: NUMERIC, 1) AS Score
       FROM checkpoint_score cs
         INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
         LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id and c.checklist_id = cl.id
         LEFT OUTER JOIN measurable_element me on me.id = c.measurable_element_id
         LEFT OUTER JOIN standard s on s.id = me.standard_id
         LEFT OUTER JOIN area_of_concern aoc on aoc.id = s.area_of_concern_id
         LEFT OUTER JOIN department d ON d.id = cl.department_id
         LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
         LEFT OUTER JOIN facility ON fa.facility_id = facility.id
       WHERE facility.name = ''Mungeli District Hospital''
       GROUP BY d.name, aoc.name ORDER BY d.name, aoc.name',
       'SELECT distinct name from area_of_concern'
     ) AS ct(Department VARCHAR(255), "Inputs" TEXT, "Patient Rights" TEXT, "Support Services" TEXT, "Infection Control" TEXT, "Quality  Management" TEXT, "Service Provision" TEXT, "Quality Management" TEXT, "Clinical Services" TEXT, "Outcome" TEXT);

-- GET facility, checklist and assessments
-- 3	Labour Room
-- 4	Maternity Ward
-- 9 PP Unit
SELECT DISTINCT
  facility.name  facility,
  facility_assessment.id,
  checklist.id   checklistId,
  checklist.name checklist
FROM facility, facility_assessment, checklist, checkpoint_score
WHERE facility_assessment.facility_id = facility.id AND checkpoint_score.checklist_id = checklist.id AND checkpoint_score.checklist_id = checklist.id AND
      checkpoint_score.facility_assessment_id = facility_assessment.id
ORDER BY facility.name, facility_assessment.id, checklist;

SELECT count(*)
FROM checkpoint_score;

CREATE OR REPLACE VIEW CHC_Department AS
  SELECT department.name
  FROM department, checklist, assessment_tool
  WHERE checklist.department_id = department.id AND assessment_tool.id = checklist.assessment_tool_id AND assessment_tool.name = 'Community Hospital (CH)';
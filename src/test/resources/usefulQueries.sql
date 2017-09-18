-- ASSESSMENT TOOLS AND CHECKLIST
SELECT
  s.name  State,
  at.name AssessmentTool,
  cl.name Checklist
FROM assessment_tool at, checklist cl, state s
WHERE cl.assessment_tool_id = at.id AND cl.state_id = s.id
ORDER BY s.name, at.name, cl.name;

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
  s.name         State,
  at.name        AssessmentTool,
  cl.name        Checklist,
  count(c.id) AS NumCheckpoints
FROM checkpoint c
  INNER JOIN checklist cl ON c.checklist_id = cl.id
  INNER JOIN state s ON cl.state_id = s.id
  INNER JOIN assessment_tool at ON at.id = cl.assessment_tool_id
GROUP BY s.name, at.name, cl.name
ORDER BY s.name, at.name, cl.name;

-- Verify the hierarchy visually by running the following query
SELECT
  at.mode       AS AssessmentMode,
  at.name       AS AssessmentTool,
  c.name        AS Checklist,
  aoc.reference AS AOC,
  s.reference   AS Standard,
  me.reference  AS ME
FROM checklist c, area_of_concern aoc, checklist_area_of_concern caoc, standard s, measurable_element me, checkpoint cp, assessment_tool at
WHERE cp.checklist_id = c.id AND caoc.checklist_id = c.id AND aoc.id = caoc.area_of_concern_id AND s.area_of_concern_id = aoc.id AND me.standard_id = s.id AND
      cp.measurable_element_id = me.id AND c.assessment_tool_id = at.id AND at.name = 'nqas'
ORDER BY AssessmentMode, AssessmentTool, Checklist, AOC, Standard, ME;

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
SELECT DISTINCT
  state.name             state,
  facility.id            facilityId,
  facility.name          facility,
  facility_assessment.id assessmentId,
  checklist.id           checklistId,
  checklist.name         checklist
FROM facility, facility_assessment, checklist, checkpoint_score, state
WHERE facility_assessment.facility_id = facility.id AND checkpoint_score.checklist_id = checklist.id AND checkpoint_score.checklist_id = checklist.id AND
      checkpoint_score.facility_assessment_id = facility_assessment.id AND state.id = checklist.state_id
ORDER BY facility.name, facility_assessment.id, checklist;

-- GET all assessments for facilities
SELECT DISTINCT
  state.name             state,
  facility.name          facility,
  assessment_tool.name   assessment_tool,
  facility_assessment.id assessmentId,
  facility.id            facilityId
FROM facility, facility_assessment, state, district, assessment_tool
WHERE facility_assessment.facility_id = facility.id AND facility.district_id = district.id AND district.state_id = state.id AND
      facility_assessment.assessment_tool_id = assessment_tool.id
ORDER BY facility.name, facility_assessment.id;

SELECT format('%s (%s)', aoc.name, aoc.reference) AS foo
FROM area_of_concern aoc;


SELECT
  x.Department AS Department,
  x.Score      AS Score
FROM
  (SELECT
     dws.Department AS Department,
     dws.Score      AS Score
   FROM
     (SELECT
        d.name                                                                            AS Department,
        (sum(cs.score) :: FLOAT / (2 * count(cs.score) :: FLOAT) :: FLOAT * 100 :: FLOAT) AS Score,
        max(fa.start_date)
      FROM checkpoint_score cs
        INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
        LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id
        LEFT OUTER JOIN department d ON d.id = cl.department_id
        LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
        LEFT OUTER JOIN facility ON fa.facility_id = facility.id
        LEFT OUTER JOIN assessment_tool ON fa.assessment_tool_id = assessment_tool.id
        LEFT OUTER JOIN assessment_tool_mode ON assessment_tool_mode.id = assessment_tool.assessment_tool_mode_id
      WHERE {{Facility}} AND fa.series_name = {{Series}} AND {{AssessmentMode}}
      GROUP BY D.name ORDER BY Score) AS dws
   UNION
   SELECT
     'OVERALL'                                               AS Department,
     (sum(ad.dscore) :: FLOAT) / (count(ad.dscore)) :: FLOAT AS Score
   FROM
     (
       SELECT
         d.name                                                                            AS Department,
         (sum(cs.score) :: FLOAT / (2 * count(cs.score) :: FLOAT) :: FLOAT * 100 :: FLOAT) AS dscore,
         max(fa.start_date)
       FROM checkpoint_score cs
         INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
         LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id
         LEFT OUTER JOIN department d ON d.id = cl.department_id
         LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
         LEFT OUTER JOIN facility ON fa.facility_id = facility.id
         LEFT OUTER JOIN assessment_tool ON fa.assessment_tool_id = assessment_tool.id
         LEFT OUTER JOIN assessment_tool_mode ON assessment_tool_mode.id = assessment_tool.assessment_tool_mode_id
       WHERE {{Facility}} AND fa.series_name = {{Series}} AND {{AssessmentMode}}
       GROUP BY D.name
     ) AS ad) AS x
ORDER BY x.Score


SELECT *
FROM facility_assessment;
UPDATE facility_assessment
SET last_modified_date = current_timestamp
WHERE id = 47;
SELECT *
FROM assessment_tool;
SELECT *
FROM state;
SELECT DISTINCT checklist.name
FROM checklist
ORDER BY checklist.name;

SELECT
  s.uuid   standard_uuid,
  aoc.uuid area_of_concern_uuid,
  ch.uuid  checklist_uuid
FROM standard s
  INNER JOIN area_of_concern aoc ON s.area_of_concern_id = aoc.id
  INNER JOIN checklist_area_of_concern caoc ON aoc.id = caoc.area_of_concern_id
  INNER JOIN checklist ch ON caoc.checklist_id = ch.id
  INNER JOIN assessment_tool at ON ch.assessment_tool_id = at.id
  INNER JOIN state st ON ch.state_id = st.id
WHERE st.name = 'Chhattisgarh' AND ch.name = 'Lab' AND aoc.name = 'Inputs' AND s.reference = 'C2';


SELECT
  std.uuid           standard_uuid,
  aoc.uuid           aocUUID,
  ch.uuid            checklistUUID,
  count(c.id)     AS total,
  sum(CASE WHEN cs.checkpoint_id IS NULL
    THEN 0
      ELSE 1 END) AS completed
FROM checkpoint c
  INNER JOIN measurable_element me ON c.measurable_element_id = me.id
  INNER JOIN standard std ON me.standard_id = std.id
  INNER JOIN area_of_concern aoc ON std.area_of_concern_id = aoc.id
  INNER JOIN checklist ch ON c.checklist_id = ch.id
  LEFT OUTER JOIN (SELECT DISTINCT checkpoint_id
                   FROM checkpoint_score WHERE checkpoint_score.facility_assessment_id = 47) AS cs ON c.id = cs.checkpoint_id
  WHERE ch.assessment_tool_id = 1 AND ch.state_id = 1 AND std.reference = 'C1' AND ch.name = 'Lab'
GROUP BY std.id, ch.id, aoc.id;




SELECT
  std.uuid           uuid,
  aoc.uuid           aocUUID,
  ch.uuid            checklistUUID,
  count(c.id)     AS total,
  sum(CASE WHEN cs.checkpoint_id IS NULL
    THEN 0
      ELSE 1 END) AS completed
FROM checkpoint c
  INNER JOIN measurable_element me ON c.measurable_element_id = me.id
  INNER JOIN standard std ON me.standard_id = std.id
  INNER JOIN area_of_concern aoc ON std.area_of_concern_id = aoc.id
  INNER JOIN checklist ch ON c.checklist_id = ch.id AND ch.assessment_tool_id = 1 AND ch.state_id = 1
  LEFT OUTER JOIN (SELECT DISTINCT checkpoint_id
                   FROM checkpoint_score
                   WHERE checkpoint_score.facility_assessment_id = 47) AS cs ON c.id = cs.checkpoint_id
--   WHERE std.uuid = 'f9dd5ee1-8c7d-468f-8357-7f4a70f8992f' and ch.uuid = '956db2ed-d5dc-41b5-8c9e-45bec30daf00'
GROUP BY std.id, ch.id, aoc.id;

SELECT standard_id from measurable_element WHERE reference like '%..%';

SELECT * from facility_assessment WHERE id = 47;

DELETE from checkpoint_score where facility_assessment_id != 47;
DELETE from facility_assessment where id != 47;










SELECT
  fa.id as facilityAssessmentId,
  std.uuid           uuid,
  aoc.uuid           aocUUID,
  ch.uuid            checklistUUID,
  count(c.id)     AS total,
  sum(CASE WHEN cs.checkpoint_id IS NULL
    THEN 0
      ELSE 1 END) AS completed
FROM checkpoint c
  INNER JOIN measurable_element me ON c.measurable_element_id = me.id
  INNER JOIN standard std ON me.standard_id = std.id
  INNER JOIN area_of_concern aoc ON std.area_of_concern_id = aoc.id
  INNER JOIN checklist ch ON c.checklist_id = ch.id AND ch.assessment_tool_id = 1 AND ch.state_id = 1
  LEFT OUTER JOIN (SELECT DISTINCT checkpoint_id
                   FROM checkpoint_score
                   WHERE checkpoint_score.facility_assessment_id = 47) AS cs ON c.id = cs.checkpoint_id
  LEFT OUTER JOIN facility_assessment fa ON fa.id = 47
GROUP BY std.id, ch.id, aoc.id, facilityAssessmentId;
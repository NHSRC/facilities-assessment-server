SELECT
  dws."Department" AS "Department",
  dws."Score"      AS "Score"
FROM
  (SELECT
     d.name                                                                            AS "Department",
     (sum(cs.score) :: FLOAT / (2 * count(cs.score) :: FLOAT) :: FLOAT * 100 :: FLOAT) AS "Score",
     max(fa.start_date)
   FROM checkpoint_score cs
     INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
     LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id
     LEFT OUTER JOIN department d ON d.id = cl.department_id
     LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
     LEFT OUTER JOIN facility f ON fa.facility_id = f.id
   WHERE f.name={{facility_name}}
   GROUP BY d.name) AS dws
UNION
SELECT
  'Overall'                                               AS "Department",
  (sum(ad.dscore) :: FLOAT) / (count(ad.dscore)) :: FLOAT AS "Score"
FROM
  (
    SELECT
      d.name                                                                            AS department,
      (sum(cs.score) :: FLOAT / (2 * count(cs.score) :: FLOAT) :: FLOAT * 100 :: FLOAT) AS dscore,
      max(fa.start_date)
    FROM checkpoint_score cs
      INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
      LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id
      LEFT OUTER JOIN department d ON d.id = cl.department_id
      LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
      LEFT OUTER JOIN facility f ON fa.facility_id = f.id
      WHERE f.name={{facility_name}}
    GROUP BY d.name
  ) AS ad;
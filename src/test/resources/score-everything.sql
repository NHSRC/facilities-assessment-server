delete from area_of_concern_score;
insert into area_of_concern_score (area_of_concern_id, facility_assessment_id, score)
  SELECT
    area_of_concern.id,
    facility_assessment.id,
    round(CAST((CAST(sum(checkpoint_score.score) AS float8) / (CAST(2 * count(checkpoint_score.score) AS float8)) * CAST(100 AS float8)) AS numeric), 1)
  FROM checkpoint_score
    INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id
    LEFT OUTER JOIN checklist ON checklist.id = checkpoint_score.checklist_id
    LEFT OUTER JOIN measurable_element ON measurable_element.id = checkpoint.measurable_element_id
    LEFT OUTER JOIN standard ON standard.id = measurable_element.standard_id
    LEFT OUTER JOIN area_of_concern ON area_of_concern.id = standard.area_of_concern_id
    LEFT OUTER JOIN facility_assessment ON checkpoint_score.facility_assessment_id = facility_assessment.id
  where checkpoint_score.na = false and checkpoint.inactive = false
  GROUP BY facility_assessment.id, area_of_concern.id;

delete from standard_score;
insert into standard_score (standard_id, facility_assessment_id, score)
  SELECT
    standard.id,
    facility_assessment.id,
    round(CAST((CAST(sum(checkpoint_score.score) AS float8) / (CAST(2 * count(checkpoint_score.score) AS float8)) * CAST(100 AS float8)) AS numeric), 1)
  FROM checkpoint_score
    INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id
    LEFT OUTER JOIN checklist ON checklist.id = checkpoint_score.checklist_id
    LEFT OUTER JOIN measurable_element ON measurable_element.id = checkpoint.measurable_element_id
    LEFT OUTER JOIN standard ON standard.id = measurable_element.standard_id
    LEFT OUTER JOIN facility_assessment ON checkpoint_score.facility_assessment_id = facility_assessment.id
  WHERE checkpoint_score.na = false and checkpoint.inactive = false
  GROUP BY facility_assessment.id, standard.id;

delete from checklist_score;
insert into checklist_score (checklist_id, standard_id, area_of_concern_id, facility_assessment_id, numerator, denominator)
  SELECT
    checklist.id,
    standard.id,
    area_of_concern.id,
    facility_assessment.id,
    sum(checkpoint_score.score),
    (2 * count(checkpoint_score.id))
  FROM checkpoint_score
    INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id
    LEFT OUTER JOIN checklist ON checklist.id = checkpoint_score.checklist_id
    LEFT OUTER JOIN measurable_element ON measurable_element.id = checkpoint.measurable_element_id
    LEFT OUTER JOIN standard ON standard.id = measurable_element.standard_id
    LEFT OUTER JOIN area_of_concern ON area_of_concern.id = standard.area_of_concern_id
    LEFT OUTER JOIN facility_assessment ON checkpoint_score.facility_assessment_id = facility_assessment.id
  WHERE checkpoint_score.na = false and checkpoint.inactive = false
  GROUP BY facility_assessment.id, checklist.id, area_of_concern.id, standard.id order by checklist.id;
CREATE OR REPLACE VIEW checkpoint_scores AS
  SELECT
    assessment_tool_mode.id AS assessment_type,
    fa.series_name          AS assessment_number,
    facility.id             AS facility,
    d.id                    AS department,
    c.name                  AS checkpoint,
    cs.score                AS score
  FROM checkpoint_score cs
    INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
    LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id
    LEFT OUTER JOIN department d ON d.id = cl.department_id
    LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
    LEFT OUTER JOIN facility ON fa.facility_id = facility.id
    LEFT OUTER JOIN assessment_tool ON fa.assessment_tool_id = assessment_tool.id
    LEFT OUTER JOIN assessment_tool_mode ON assessment_tool_mode.id = assessment_tool.assessment_tool_mode_id;

CREATE OR REPLACE VIEW checkpoint_scores_aoc AS
  SELECT
    assessment_tool_mode.id                    AS assessment_type,
    fa.series_name                             AS assessment_number,
    facility.id                                AS facility,
    format('%s (%s)', aoc.reference, aoc.name) AS area_of_concern,
    department.id                              AS department,
    c.name                                     AS checkpoint,
    cs.score                                   AS score
  FROM checkpoint_score cs
    INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
    LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id
    LEFT OUTER JOIN department ON cl.department_id = department.id
    LEFT OUTER JOIN measurable_element me ON me.id = c.measurable_element_id
    LEFT OUTER JOIN standard s ON s.id = me.standard_id
    LEFT OUTER JOIN area_of_concern aoc ON aoc.id = s.area_of_concern_id
    LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
    LEFT OUTER JOIN facility ON fa.facility_id = facility.id
    LEFT OUTER JOIN assessment_tool ON fa.assessment_tool_id = assessment_tool.id
    LEFT OUTER JOIN assessment_tool_mode ON assessment_tool_mode.id = assessment_tool.assessment_tool_mode_id;
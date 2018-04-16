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
    assessment_tool_mode.id                                  AS assessment_type,
    fa.series_name                                           AS assessment_number,
    facility.id                                              AS facility,
    format('%s (%s)', aoc.reference, aoc.name)               AS area_of_concern,
    department.id                                            AS department,
    c.name                                                   AS checkpoint,
    cs.score                                                 AS score,
    state.id                                                 AS state,
    facility_type.id                                         AS facility_type,
    s.reference                                              AS standard,
    s.name                                                   AS standard_name,
    format('[%s, %s] - %s', aoc.reference, aoc.name, s.name) AS standard_description
  FROM checkpoint_score cs
    INNER JOIN checkpoint c ON cs.checkpoint_id = c.id
    LEFT OUTER JOIN checklist cl ON cl.id = cs.checklist_id
    LEFT OUTER JOIN department ON cl.department_id = department.id
    LEFT OUTER JOIN measurable_element me ON me.id = c.measurable_element_id
    LEFT OUTER JOIN standard s ON s.id = me.standard_id
    LEFT OUTER JOIN area_of_concern aoc ON aoc.id = s.area_of_concern_id
    LEFT OUTER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
    LEFT OUTER JOIN facility ON fa.facility_id = facility.id
    LEFT OUTER JOIN facility_type ON facility.facility_type_id = facility_type.id
    LEFT OUTER JOIN district ON facility.district_id = district.id
    LEFT OUTER JOIN state ON district.state_id = state.id
    LEFT OUTER JOIN assessment_tool ON fa.assessment_tool_id = assessment_tool.id
    LEFT OUTER JOIN assessment_tool_mode ON assessment_tool_mode.id = assessment_tool.assessment_tool_mode_id;

drop VIEW IF EXISTS checkpoint_denormalised;
CREATE OR REPLACE VIEW checkpoint_denormalised AS
  SELECT
    checkpoint.id           checkpoint_id,
    measurable_element.id   measurable_element_id,
    standard.id             standard_id,
    area_of_concern.id      area_of_concern_id,
    checklist.id            checklist_id,
    assessment_tool.id      assessment_tool_id,
    assessment_tool_mode.id assessment_tool_mode_id
  FROM checkpoint
    INNER JOIN measurable_element ON checkpoint.measurable_element_id = measurable_element.id
    INNER JOIN standard ON measurable_element.standard_id = standard.id
    INNER JOIN area_of_concern ON standard.area_of_concern_id = area_of_concern.id
    INNER JOIN checklist ON checkpoint.checklist_id = checklist.id
    INNER JOIN assessment_tool ON checklist.assessment_tool_id = assessment_tool.id
    INNER JOIN assessment_tool_mode ON assessment_tool.assessment_tool_mode_id = assessment_tool_mode.id;

CREATE OR REPLACE VIEW checkpoint_score_denormalised AS
  SELECT
    checkpoint_score.id     checkpoint_score_id,
    facility_assessment.id  facility_assessment_id,
    measurable_element.id   measurable_element_id,
    standard.id             standard_id,
    area_of_concern.id      area_of_concern_id,
    checklist.id            checklist_id,
    assessment_tool.id      assessment_tool_id,
    assessment_tool_mode.id assessment_tool_mode_id,
    state.id                state_id
  FROM checkpoint_score
    INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id
    INNER JOIN measurable_element ON checkpoint.measurable_element_id = measurable_element.id
    INNER JOIN standard ON measurable_element.standard_id = standard.id
    INNER JOIN area_of_concern ON standard.area_of_concern_id = area_of_concern.id
    INNER JOIN checklist ON checkpoint.checklist_id = checklist.id AND checkpoint_score.checklist_id = checklist.id
    INNER JOIN assessment_tool ON checklist.assessment_tool_id = assessment_tool.id
    INNER JOIN assessment_tool_mode ON assessment_tool.assessment_tool_mode_id = assessment_tool_mode.id
    INNER JOIN facility_assessment ON assessment_tool.id = facility_assessment.assessment_tool_id AND checkpoint_score.facility_assessment_id = facility_assessment.id
    INNER JOIN facility ON facility_assessment.facility_id = facility.id
    INNER JOIN district ON facility.district_id = district.id
    INNER JOIN state ON district.state_id = state.id AND checklist.state_id = state.id;

CREATE OR REPLACE VIEW assessment_denormalised AS
  SELECT
    facility_assessment.id          facility_assessment,
    assessment_tool.id              assessment_tool,
    assessment_tool_mode.id         assessment_type,
    facility_assessment.series_name series,
    facility.id                     facility,
    facility_type.id                facility_type,
    district.id                     district,
    state.id                        state
  FROM facility_assessment
    INNER JOIN assessment_tool ON facility_assessment.assessment_tool_id = assessment_tool.id
    INNER JOIN assessment_tool_mode ON assessment_tool.assessment_tool_mode_id = assessment_tool_mode.id
    INNER JOIN facility ON facility_assessment.facility_id = facility.id
    INNER JOIN district ON facility.district_id = district.id
    INNER JOIN state ON district.state_id = state.id
    INNER JOIN facility_type ON facility.facility_type_id = facility_type.id;

DROP VIEW if exists checklist_score_view;
CREATE VIEW checklist_score_view AS
  SELECT
    assessment_tool_mode.id                                  AS program,
    fa.assessment_code                                       AS assessment_code,
    fa.series_name                                           AS assessment_number,
    facility.id                                              AS facility,
    assessment_type.id                                       AS assessment_type,
    facility_type.id                                         AS facility_type,
    aoc.id                                                   AS area_of_concern,
    format('%s (%s)', aoc.reference, aoc.name)               AS area_of_concern_display,
    cl.id                                                    AS checklist,
    cl.name                                                  AS checklist_name,
    cs.numerator                                             AS numerator,
    cs.denominator                                           AS denominator,
    s.id                                                     AS standard,
    s.reference                                              AS standard_reference,
    s.name                                                   AS standard_name,
    format('[%s, %s] - %s', aoc.reference, aoc.name, s.name) AS standard_description,
    state.id                                                 AS state
  FROM checklist_score cs
    INNER JOIN checklist cl ON cl.id = cs.checklist_id
    INNER JOIN standard s ON s.id = cs.standard_id
    INNER JOIN area_of_concern aoc ON aoc.id = cs.area_of_concern_id
    INNER JOIN facility_assessment fa ON cs.facility_assessment_id = fa.id
    INNER JOIN facility ON fa.facility_id = facility.id
    INNER JOIN facility_type ON facility.facility_type_id = facility_type.id
    INNER JOIN district ON facility.district_id = district.id
    INNER JOIN state ON district.state_id = state.id
    INNER JOIN assessment_tool ON fa.assessment_tool_id = assessment_tool.id
    INNER JOIN assessment_tool_mode ON assessment_tool_mode.id = assessment_tool.assessment_tool_mode_id
    INNER JOIN assessment_type ON assessment_type.id = fa.assessment_type_id;

DROP VIEW if exists area_of_concern_score_view;
CREATE VIEW area_of_concern_score_view AS
  SELECT
    assessment_tool_mode.id                    AS assessment_type,
    fa.assessment_code                         AS assessment_code,
    fa.series_name                             AS assessment_number,
    facility.id                                AS facility,
    format('%s (%s)', aoc.reference, aoc.name) AS area_of_concern,
    aocs.score                                 AS score,
    state.id                                   AS state,
    facility_type.id                           AS facility_type
  FROM area_of_concern_score aocs
    INNER JOIN area_of_concern aoc ON aoc.id = aocs.area_of_concern_id
    INNER JOIN facility_assessment fa ON aocs.facility_assessment_id = fa.id
    INNER JOIN facility ON fa.facility_id = facility.id
    INNER JOIN facility_type ON facility.facility_type_id = facility_type.id
    INNER JOIN district ON facility.district_id = district.id
    INNER JOIN state ON district.state_id = state.id
    INNER JOIN assessment_tool ON fa.assessment_tool_id = assessment_tool.id
    INNER JOIN assessment_tool_mode ON assessment_tool_mode.id = assessment_tool.assessment_tool_mode_id;

DROP VIEW if exists standard_score_view;
CREATE VIEW standard_score_view AS
  SELECT
    assessment_tool_mode.id AS assessment_type,
    fa.assessment_code      AS assessment_code,
    fa.series_name          AS assessment_number,
    facility.id             AS facility,
    s.reference             AS standard,
    s.name                  AS standard_name,
    ss.score                AS score,
    state.id                AS state,
    facility_type.id        AS facility_type
  FROM standard_score ss
    INNER JOIN standard s ON s.id = ss.standard_id
    INNER JOIN facility_assessment fa ON ss.facility_assessment_id = fa.id
    INNER JOIN facility ON fa.facility_id = facility.id
    INNER JOIN facility_type ON facility.facility_type_id = facility_type.id
    INNER JOIN district ON facility.district_id = district.id
    INNER JOIN state ON district.state_id = state.id
    INNER JOIN assessment_tool ON fa.assessment_tool_id = assessment_tool.id
    INNER JOIN assessment_tool_mode ON assessment_tool_mode.id = assessment_tool.assessment_tool_mode_id;
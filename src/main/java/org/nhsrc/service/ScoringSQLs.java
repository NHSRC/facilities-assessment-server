package org.nhsrc.service;

class ScoringSQLs {
    static final String Delete_Checklist_Scores = "delete from checklist_score where facility_assessment_id = :assessmentId";
    static final String Delete_Checklist_Only_Scores = "delete from checklist_only_score where facility_assessment_id = :assessmentId";
    static final String Delete_Standard_Scores = "delete from standard_score where facility_assessment_id = :assessmentId";
    static final String Delete_AreaOfConcern_Scores = "delete from area_of_concern_score where facility_assessment_id = :assessmentId";
    static final String Delete_Assessment_Scores = "delete from facility_assessment_score where facility_assessment_id = :assessmentId";

    static final String AssessmentId_Param = "assessmentId";

    static final String Create_Checklist_Scores = "insert into checklist_score (checklist_id, standard_id, area_of_concern_id, facility_assessment_id, numerator, denominator)\n" +
            "SELECT\n" +
            "  checklist.id,\n" +
            "  standard.id,\n" +
            "  area_of_concern.id,\n" +
            "  facility_assessment.id,\n" +
            "  sum(checkpoint_score.score),\n" +
            "  (2 * count(checkpoint_score.id))\n" +
            "    FROM checkpoint_score\n" +
            "    INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id\n" +
            "    LEFT OUTER JOIN checklist ON checklist.id = checkpoint_score.checklist_id\n" +
            "    LEFT OUTER JOIN measurable_element ON measurable_element.id = checkpoint.measurable_element_id\n" +
            "    LEFT OUTER JOIN standard ON standard.id = measurable_element.standard_id\n" +
            "    LEFT OUTER JOIN area_of_concern ON area_of_concern.id = standard.area_of_concern_id\n" +
            "    LEFT OUTER JOIN facility_assessment ON checkpoint_score.facility_assessment_id = facility_assessment.id\n" +
            "    WHERE facility_assessment.id = :assessmentId and checkpoint_score.na = false and checkpoint.inactive = false\n" +
            "    GROUP BY facility_assessment.id, checklist.id, area_of_concern.id, standard.id order by checklist.id;\n";

    static final String Create_Checklist_Only_Scores = "insert into checklist_only_score (checklist_id, facility_assessment_id, score)\n" +
            "SELECT\n" +
            "  checklist.id,\n" +
            "  facility_assessment.id,\n" +
            "  round(cast(sum(checkpoint_score.score)*100 as numeric) / (2 * count(checkpoint_score.id)),2)\n" +
            "    FROM checkpoint_score\n" +
            "    INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id\n" +
            "    LEFT OUTER JOIN checklist ON checklist.id = checkpoint_score.checklist_id\n" +
            "    LEFT OUTER JOIN facility_assessment ON checkpoint_score.facility_assessment_id = facility_assessment.id\n" +
            "    WHERE facility_assessment.id = :assessmentId and checkpoint_score.na = false and checkpoint.inactive = false\n" +
            "    GROUP BY facility_assessment.id, checklist.id order by checklist.id;\n";

    static final String Create_Standard_Scores = "insert into standard_score (standard_id, facility_assessment_id, score)\n" +
            "  SELECT\n" +
            "    standard.id,\n" +
            "    facility_assessment.id,\n" +
            "    round(cast(sum(checkpoint_score.score)*100 as numeric) / (2 * count(checkpoint_score.score)), 2)\n" +
            "  FROM checkpoint_score\n" +
            "    INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id\n" +
            "    LEFT OUTER JOIN checklist ON checklist.id = checkpoint_score.checklist_id\n" +
            "    LEFT OUTER JOIN measurable_element ON measurable_element.id = checkpoint.measurable_element_id\n" +
            "    LEFT OUTER JOIN standard ON standard.id = measurable_element.standard_id\n" +
            "    LEFT OUTER JOIN facility_assessment ON checkpoint_score.facility_assessment_id = facility_assessment.id\n" +
            "  WHERE facility_assessment.id = :assessmentId and checkpoint_score.na = false and checkpoint.inactive = false\n" +
            "  GROUP BY facility_assessment.id, standard.id;\n";

    static final String Create_AreaOfConcern_Scores = "insert into area_of_concern_score (area_of_concern_id, facility_assessment_id, score)\n" +
            "SELECT\n" +
            "  area_of_concern.id,\n" +
            "  facility_assessment.id,\n" +
            "  round(cast(sum(checkpoint_score.score)*100 as numeric) / (2 * count(checkpoint_score.score)), 2)\n" +
            "FROM checkpoint_score\n" +
            "  INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id\n" +
            "  LEFT OUTER JOIN checklist ON checklist.id = checkpoint_score.checklist_id\n" +
            "  LEFT OUTER JOIN measurable_element ON measurable_element.id = checkpoint.measurable_element_id\n" +
            "  LEFT OUTER JOIN standard ON standard.id = measurable_element.standard_id\n" +
            "  LEFT OUTER JOIN area_of_concern ON area_of_concern.id = standard.area_of_concern_id\n" +
            "  LEFT OUTER JOIN facility_assessment ON checkpoint_score.facility_assessment_id = facility_assessment.id\n" +
            "where facility_assessment.id = :assessmentId and checkpoint_score.na = false and checkpoint.inactive = false\n" +
            "GROUP BY facility_assessment.id, area_of_concern.id";

    static final String Create_Assessment_Scores = "insert into facility_assessment_score (facility_assessment_id, score)\n" +
            "SELECT\n" +
            "  facility_assessment.id,\n" +
            "  round(cast(sum(checkpoint_score.score)*100 as numeric) / (2 * count(checkpoint_score.score)), 2)\n" +
            "FROM checkpoint_score\n" +
            "  INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id\n" +
            "  LEFT OUTER JOIN facility_assessment ON checkpoint_score.facility_assessment_id = facility_assessment.id\n" +
            "where facility_assessment.id = :assessmentId and checkpoint_score.na = false and checkpoint.inactive = false\n" +
            "GROUP BY facility_assessment.id";
}

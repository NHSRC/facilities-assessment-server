package org.nhsrc.service;

public class ScoringSQLs {
    public static final String Delete_Checklist_Scores = "delete from checklist_score where facility_assessment_id = :assessmentId";
    public static final String Delete_Standard_Scores = "delete from standard_score where facility_assessment_id = :assessmentId";
    public static final String Delete_AreaOfConcern_Scores = "delete from area_of_concern_score where facility_assessment_id = :assessmentId";

    public static final String FacilityId_Param = "facilityId";

    public static final String Create_Checklist_Scores = "insert into checklist_score (checklist_id, standard_id, area_of_concern_id, facility_assessment_id, numerator, denominator)\n" +
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
            "    WHERE facility_assessment.id = :assessmentId and checkpoint_score.na = false\n" +
            "    GROUP BY checklist.id, area_of_concern.id, standard.id order by checklist.id;\n";

    public static final String Create_Standard_Scores = "insert into standard_score (standard_id, facility_assessment_id, score)\n" +
            "SELECT\n" +
            "  standard.id,\n" +
            "  facility_assessment.id,\n" +
            "  round((sum(checkpoint_score.score) :: FLOAT / (2 * count(checkpoint_score.score) :: FLOAT) :: FLOAT * 100 :: FLOAT), 1)\n" +
            "FROM checkpoint_score\n" +
            "  INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id\n" +
            "  LEFT OUTER JOIN checklist ON checklist.id = checkpoint_score.checklist_id\n" +
            "  LEFT OUTER JOIN measurable_element ON measurable_element.id = checkpoint.measurable_element_id\n" +
            "  LEFT OUTER JOIN standard ON standard.id = measurable_element.standard_id\n" +
            "  LEFT OUTER JOIN facility_assessment ON checkpoint_score.facility_assessment_id = facility_assessment.id\n" +
            "WHERE facility_assessment.id = :assessmentId and checkpoint_score.na = false\n" +
            "GROUP BY standard.id;\n";

    public static final String Create_AreaOfConcern_Scores = "insert into area_of_concern_score (area_of_concern_id, facility_assessment_id, score)\n" +
            "SELECT\n" +
            "  area_of_concern.id,\n" +
            "  facility_assessment.id,\n" +
            "  round((sum(checkpoint_score.score) :: FLOAT / (2 * count(checkpoint_score.score) :: FLOAT) :: FLOAT * 100 :: FLOAT), 1)\n" +
            "FROM checkpoint_score\n" +
            "  INNER JOIN checkpoint ON checkpoint_score.checkpoint_id = checkpoint.id\n" +
            "  LEFT OUTER JOIN checklist ON checklist.id = checkpoint_score.checklist_id\n" +
            "  LEFT OUTER JOIN measurable_element ON measurable_element.id = checkpoint.measurable_element_id\n" +
            "  LEFT OUTER JOIN standard ON standard.id = measurable_element.standard_id\n" +
            "  LEFT OUTER JOIN area_of_concern ON area_of_concern.id = standard.area_of_concern_id\n" +
            "  LEFT OUTER JOIN facility_assessment ON checkpoint_score.facility_assessment_id = facility_assessment.id\n" +
            "where facility_assessment.id = :assessmentId and checkpoint_score.na = false\n" +
            "GROUP BY area_of_concern.id";
}
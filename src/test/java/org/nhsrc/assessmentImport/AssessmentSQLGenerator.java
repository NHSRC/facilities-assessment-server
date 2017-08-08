package org.nhsrc.assessmentImport;

import org.nhsrc.domain.*;
import org.nhsrc.referenceDataImport.AssessmentChecklistData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AssessmentSQLGenerator {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static void generate(AssessmentChecklistData assessmentChecklistData, File outputFile, boolean generateFacilityAssessmentSQL) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        FacilityAssessment assessment = assessmentChecklistData.getAssessment();
        if (generateFacilityAssessmentSQL)
            generateFacilityAssessmentSQL(assessment, stringBuffer);
        generateCheckpointScoreSQL(assessmentChecklistData, stringBuffer);

        writeToFile(outputFile, stringBuffer);
    }

    private static void writeToFile(File outputFile, StringBuffer stringBuffer) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile))) {
            bufferedWriter.write(stringBuffer.toString());
        }
    }

    private static void generateCheckpointScoreSQL(AssessmentChecklistData assessmentChecklistData, StringBuffer stringBuffer) {
        FacilityAssessment assessment = assessmentChecklistData.getAssessment();
        assessmentChecklistData.getCheckpointScores().forEach(checkpointScore -> {
            String assessmentToolName = assessmentChecklistData.getAssessmentTool().getName();
            Checkpoint checkpoint = checkpointScore.getCheckpoint();
            MeasurableElement measurableElement = checkpoint.getMeasurableElement();
            Standard standard = measurableElement.getStandard();
            AreaOfConcern areaOfConcern = standard.getAreaOfConcern();
            String stateName = assessmentChecklistData.getState().getName();
            String checklistName = checkpointScore.getChecklist().getName();

            String checkpointIDQUery = "select checkpoint.id from checkpoint, measurable_element, standard, area_of_concern, checklist_area_of_concern, checklist, assessment_tool where checkpoint.measurable_element_id = measurable_element.id and measurable_element.reference = '%s' and measurable_element.standard_id = standard.id and standard.reference = '%s' and standard.area_of_concern_id = area_of_concern.id and area_of_concern.reference = '%s' and area_of_concern.assessment_tool_id = assessment_tool.id AND assessment_tool.name = '%s' and checkpoint.name = '%s' and checklist.id = checklist_area_of_concern.checklist_id and area_of_concern.id = checklist_area_of_concern.area_of_concern_id and checklist.name = '%s' and checklist.state_id = (SELECT id from state where name = '%s') and checkpoint.checklist_id = checklist.id";
            String filledQuery = String.format(checkpointIDQUery, /*4*/measurableElement.getReference(),
                /*5*/standard.getReference(),
                /*6*/areaOfConcern.getReference(),
                /*7*/assessmentToolName,
                /*8*/checkpoint.getName().replace("'", "''"),
                /*9*/checklistName,
                /*10*/stateName);

            stringBuffer.append(String.format("insert into checkpoint_score (facility_assessment_id, checkpoint_id, checklist_id, score, remarks) select (select id from facility_assessment where assessment_tool_id = (select id from assessment_tool where name = '%s') and facility_id = (select id from facility where name = '%s')), (%s), (select id from checklist where name = '%s' and state_id = (select id from state where name = '%s') and assessment_tool_id = (select id from assessment_tool where name = '%s')), %d, '%s' where exists (%s);\n",
                /*1*/assessmentToolName,
                /*3*/assessment.getFacility().getName(),
                /*4*/filledQuery,
                /*5*/checklistName,
                /*6*/stateName,
                /*7*/assessmentToolName,
                /*8*/checkpointScore.getScore(),
                /*9*/checkpointScore.getRemarks().replace("'", "''"),
                /*10*/filledQuery));
        });
    }

    private static void generateFacilityAssessmentSQL(FacilityAssessment assessment, StringBuffer stringBuffer) {
        stringBuffer.append(String.format("insert into facility_assessment (assessment_tool_id, start_date, end_date, facility_id) values ((select id from assessment_tool where name = '%s'), '%s', '%s', (select id from facility where name = '%s'));\n", assessment.getAssessmentTool().getName(), dateFormatter.format(assessment.getStartDate()), dateFormatter.format(assessment.getEndDate()), assessment.getFacility().getName()));
    }

    public static void generateVerifyCheckpointSQL(AssessmentChecklistData assessmentChecklistData, File verifySQLFile) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        List<CheckpointScore> checkpointScores = assessmentChecklistData.getCheckpointScores();
        checkpointScores.forEach(checkpointScore -> {
            stringBuffer.append(String.format("select verify_checkpoint('%s', '%s', '%s', '%s');\n", checkpointScore.getChecklist().getAssessmentTool().getName(), checkpointScore.getChecklist().getName(), checkpointScore.getCheckpoint().getMeasurableElement().getReference(), checkpointScore.getCheckpoint().getName().replace("'", "''")));
        });
        writeToFile(verifySQLFile, stringBuffer);
    }

    public static void generateVerifyChecklistSQL(AssessmentChecklistData assessmentChecklistData, File outputFile) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        List<Checklist> checklists = assessmentChecklistData.getChecklists();
        checklists.forEach(checklist -> {
            stringBuffer.append(String.format("select verify_checklist('%s', '%s');\n", checklist.getAssessmentTool().getName(), checklist.getName()));
        });
        writeToFile(outputFile, stringBuffer);
    }
}
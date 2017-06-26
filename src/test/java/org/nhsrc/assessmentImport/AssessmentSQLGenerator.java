package org.nhsrc.assessmentImport;

import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.referenceDataImport.AssessmentChecklistData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class AssessmentSQLGenerator {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    public static StringBuffer generate(AssessmentChecklistData assessmentChecklistData, File outputFile) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        FacilityAssessment assessment = assessmentChecklistData.getAssessment();
        generateFacilityAssessmentSQL(assessment, stringBuffer);
        generateCheckpointScoreSQL(assessmentChecklistData, stringBuffer);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile))) {
            bufferedWriter.write(stringBuffer.toString());
        }
        return stringBuffer;
    }

    private static void generateCheckpointScoreSQL(AssessmentChecklistData assessmentChecklistData, StringBuffer stringBuffer) {
        FacilityAssessment assessment = assessmentChecklistData.getAssessment();
        assessmentChecklistData.getCheckpointScores().forEach(checkpointScore -> {
            stringBuffer.append(String.format("insert into checkpoint_score (facility_assessment_id, checkpoint_id, checklist_id, score, remarks) values ((select id from facility_assessment where assessment_tool_id = (select id from assessment_tool where name = '%s') and start_date = '%s'), (select id from checkpoint where name = '%s'), (select id from checklist where name = '%s'), %d, '%s');\n", assessmentChecklistData.getAssessmentTool().getName(), dateFormatter.format(assessment.getStartDate()), checkpointScore.getCheckpoint().getName(), checkpointScore.getChecklist().getName(), checkpointScore.getScore(), checkpointScore.getRemarks()));
        });
    }

    private static void generateFacilityAssessmentSQL(FacilityAssessment assessment, StringBuffer stringBuffer) {
        stringBuffer.append(String.format("insert into facility_assessment (assessment_tool_id, start_date, end_date) values ((select id from assessment_tool where name = '%s'), %s, %s);\n", assessment.getAssessmentTool().getName(), dateFormatter.format(assessment.getStartDate()), dateFormatter.format(assessment.getEndDate())));
    }
}
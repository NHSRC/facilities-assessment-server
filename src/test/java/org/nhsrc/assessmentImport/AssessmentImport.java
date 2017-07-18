package org.nhsrc.assessmentImport;

import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.excel.AssessmentFile;
import org.nhsrc.excel.AssessmentsDirectory;
import org.nhsrc.referenceDataImport.AssessmentChecklistData;
import org.nhsrc.referenceDataImport.ExcelImporter;

import java.io.File;
import java.util.List;

public class AssessmentImport {
    public static void main(String[] args) {
        AssessmentImport assessmentImport = new AssessmentImport();
        assessmentImport.importAssessments();
    }

    @Test
    public void importAssessments() {
        AssessmentsDirectory assessmentsDirectory = new AssessmentsDirectory("../reference-data/jss/mp/assessments");
        List<AssessmentFile> assessmentFiles = assessmentsDirectory.getAssessmentFiles();
        assessmentFiles.forEach(assessmentFile -> {
            try {
                System.out.println();
                System.out.println(String.format("PROCESSING FILE: %s", assessmentFile.getFile().getName()));
                AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
                AssessmentTool assessmentTool = new AssessmentTool(assessmentFile.getAssessmentToolShortName(), "nqas");
                assessmentChecklistData.set(assessmentTool);

                FacilityAssessment facilityAssessment = new FacilityAssessment();
                facilityAssessment.setAssessmentTool(assessmentTool);

                Facility facility = createFacility(assessmentFile);
                facilityAssessment.setFacility(facility);
                facilityAssessment.setStartDate(assessmentFile.getStartDate().toDate());
                facilityAssessment.setEndDate(assessmentFile.getEndDate().toDate());
                assessmentChecklistData.setAssessment(facilityAssessment);

                ExcelImporter excelImporter = new ExcelImporter(assessmentChecklistData);
                excelImporter.importFile(assessmentFile.getFile(), assessmentTool, 1, true, facilityAssessment);
                String fileName = assessmentFile.getFile().getName().replaceFirst("[.][^.]+$", "");

//                AssessmentSQLGenerator.generateVerifyChecklistSQL(assessmentChecklistData, new File(assessmentsDirectory.toDirectory(), String.format("output/%s_verify_checklists.sql", facility.getName())));
//                AssessmentSQLGenerator.generateVerifyCheckpointSQL(assessmentChecklistData, new File(assessmentsDirectory.toDirectory(), String.format("output/%s_verify_checkpoints.sql", facility.getName())));
//                AssessmentSQLGenerator.generate(assessmentChecklistData, new File("../jss-assessments/output/", String.format("%s.sql", fileName)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Facility createFacility(AssessmentFile assessmentFile) {
        Facility facility = new Facility();
        facility.setFacilityType(ShortNames.getFacilityType(assessmentFile.getFacilityTypeShortName()));
        facility.setName(assessmentFile.getFacilityName());
        return facility;
    }
}
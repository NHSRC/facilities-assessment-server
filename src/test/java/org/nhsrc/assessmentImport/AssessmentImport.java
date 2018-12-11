package org.nhsrc.assessmentImport;

import org.junit.Ignore;
import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.State;
import org.nhsrc.excel.AssessmentFile;
import org.nhsrc.excel.AssessmentsDirectory;
import org.nhsrc.referenceDataImport.AssessmentChecklistData;
import org.nhsrc.referenceDataImport.ExcelImporter;

import java.io.File;
import java.util.List;

public class AssessmentImport {
    @Test @Ignore
    public void importAssessments() throws Exception {
        importAssessmentsFromFiles(1, -1, true, "output/%s.sql", true);
    }

    private void importAssessmentsFromFiles(int startingSheet, int numberOfSheetsToImport, boolean generateVerify, String outputFilePattern, boolean generateFacilityAssessmentSQL) throws Exception {
        AssessmentsDirectory assessmentsDirectory = new AssessmentsDirectory("../reference-data/jss/mp/assessments");
        List<AssessmentFile> assessmentFiles = assessmentsDirectory.getAssessmentFiles();
        for (AssessmentFile assessmentFile : assessmentFiles) {
            System.out.println(String.format("PROCESSING FILE: %s", assessmentFile.getFile().getName()));
            importAssessmentFromFile(startingSheet, numberOfSheetsToImport, generateVerify, outputFilePattern, generateFacilityAssessmentSQL, assessmentsDirectory, assessmentFile);
        }
    }

    public void importAssessmentFromFile(int startingSheet, int numberOfSheetsToImport, boolean generateVerify, String outputFilePattern, boolean generateFacilityAssessmentSQL, AssessmentsDirectory assessmentsDirectory, AssessmentFile assessmentFile) throws Exception {
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.setState(new State("Madhya Pradesh"));
        AssessmentTool assessmentTool = ShortNames.getAssessmentTool(assessmentFile.getAssessmentToolShortName());
        assessmentChecklistData.set(assessmentTool);

        FacilityAssessment facilityAssessment = new FacilityAssessment();
        facilityAssessment.setAssessmentTool(assessmentTool);

        Facility facility = createFacility(assessmentFile);
        facilityAssessment.setFacility(facility);
        facilityAssessment.setStartDate(assessmentFile.getStartDate().toDate());
        facilityAssessment.setEndDate(assessmentFile.getEndDate().toDate());
        assessmentChecklistData.setAssessment(facilityAssessment);

        ExcelImporter excelImporter = new ExcelImporter(assessmentChecklistData);
        excelImporter.importFile(assessmentFile.getFile(), assessmentTool, startingSheet, numberOfSheetsToImport, true, facilityAssessment);

        if (generateVerify) {
            AssessmentSQLGenerator.generateVerifyChecklistSQL(assessmentChecklistData, new File(assessmentsDirectory.toDirectory(), String.format("output/%s_verify_checklists.sql", facility.getName())));
            AssessmentSQLGenerator.generateVerifyCheckpointSQL(assessmentChecklistData, new File(assessmentsDirectory.toDirectory(), String.format("output/%s_verify_checkpoints.sql", facility.getName())));
        }

        String fileName = assessmentFile.getFile().getName().replaceFirst("[.][^.]+$", "");
        AssessmentSQLGenerator.generate(assessmentChecklistData, new File(assessmentsDirectory.toDirectory(), String.format(outputFilePattern, fileName)), generateFacilityAssessmentSQL);
    }

    @Test
    public void importFirstDepartment() throws Exception {
        importAssessmentsFromFiles(0, 1, false, "output2/%s_2.sql", false);
    }

    private Facility createFacility(AssessmentFile assessmentFile) {
        Facility facility = new Facility();
        facility.setFacilityType(ShortNames.getFacilityType(assessmentFile.getFacilityTypeShortName()));
        facility.setName(assessmentFile.getFacilityName());
        return facility;
    }
}
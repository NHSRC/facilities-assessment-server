package org.nhsrc.assessmentImport;

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
    @Test
    public void importAssessments() throws Exception {
        AssessmentsDirectory assessmentsDirectory = new AssessmentsDirectory("../reference-data/jss/mp/assessments");
        List<AssessmentFile> assessmentFiles = assessmentsDirectory.getAssessmentFiles();
        for (AssessmentFile assessmentFile : assessmentFiles) {
            System.out.println();
            System.out.println(String.format("PROCESSING FILE: %s", assessmentFile.getFile().getName()));
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
            excelImporter.importFile(assessmentFile.getFile(), assessmentTool, 1, true, facilityAssessment);
            String fileName = assessmentFile.getFile().getName().replaceFirst("[.][^.]+$", "");

            AssessmentSQLGenerator.generateVerifyChecklistSQL(assessmentChecklistData, new File(assessmentsDirectory.toDirectory(), String.format("output/%s_verify_checklists.sql", facility.getName())));
            AssessmentSQLGenerator.generateVerifyCheckpointSQL(assessmentChecklistData, new File(assessmentsDirectory.toDirectory(), String.format("output/%s_verify_checkpoints.sql", facility.getName())));
            AssessmentSQLGenerator.generate(assessmentChecklistData, new File(assessmentsDirectory.toDirectory(), String.format("output/%s.sql", fileName)));
        }
    }

    private Facility createFacility(AssessmentFile assessmentFile) {
        Facility facility = new Facility();
        facility.setFacilityType(ShortNames.getFacilityType(assessmentFile.getFacilityTypeShortName()));
        facility.setName(assessmentFile.getFacilityName());
        return facility;
    }
}
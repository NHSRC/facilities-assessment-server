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
    @Test
    public void importAssessments() {
        AssessmentsDirectory assessmentsDirectory = new AssessmentsDirectory("../jss-assessments/input");
        List<AssessmentFile> assessmentFiles = assessmentsDirectory.getAssessmentFiles();
        assessmentFiles.forEach(assessmentFile -> {
            try {
                System.out.println(String.format("Processing file: %s", assessmentFile.getFile().getName()));
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
                excelImporter.importFile(assessmentFile.getFile(), assessmentTool,1, true, facilityAssessment);
                String fileName = assessmentFile.getFile().getName().replaceFirst("[.][^.]+$", "");
                AssessmentSQLGenerator.generate(assessmentChecklistData, new File("../jss-assessments/output/", String.format("%s.sql", fileName)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Facility createFacility(AssessmentFile assessmentFile) {
        Facility facility = new Facility();
        facility.setFacilityType(ShortNames.getFacilityType(assessmentFile.getFacilityTypeShortName()));
        facility.setName(assessmentFile.getFacilityName());
        facility.setDistrict(assessmentFile.getDistrict());
        return facility;
    }
}
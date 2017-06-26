package org.nhsrc.referenceDataImport;

import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;

import java.io.File;

public class NHSRCDataCreator {
    @Test
    public void importNQAS_Emergency() throws Exception {
        String dirPath = "../checklists/nhsrc";
        File file = new File(dirPath, "Chececklist_DH_New_Revised May 2016.xlsx");

        ChecklistCreator checklistCreator = new ChecklistCreator();
        AssessmentChecklistData data = new AssessmentChecklistData();
        data.set(new AssessmentTool("nqas", "District Hospital (DH)"));
        checklistCreator.performImport(file, 0, data);

        file = new File(dirPath, "DH,SDH & CHC Kayakalp  28 July 2016.xlsx");
        data.set(new AssessmentTool("Kayakalp", "Kayakalp"));
        checklistCreator.performImport(file, 0, data);

//        Runtime.getRuntime().exec("psql -Unhsrc facilities_assessment_test < ../nqas.sql");
    }
}
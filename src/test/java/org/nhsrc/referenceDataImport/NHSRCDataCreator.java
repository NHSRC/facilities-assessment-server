package org.nhsrc.referenceDataImport;

import org.junit.Test;

import java.io.File;

public class NHSRCDataCreator {
    @Test
    public void importNQAS_Emergency() throws Exception {
        String dirPath = "../checklists/nhsrc";
        File file = new File(dirPath, "Chececklist_DH_New_Revised May 2016.xlsx");

        ChecklistCreator checklistCreator = new ChecklistCreator();
        AssessmentChecklistData data = new AssessmentChecklistData();
        checklistCreator.performImport("nqas", "District Hospital (DH)", file, 0, data);

        file = new File(dirPath, "DH,SDH & CHC Kayakalp  28 July 2016.xlsx");
        checklistCreator.performImport("Kayakalp", "Kayakalp", file, 0, data);

//        Runtime.getRuntime().exec("psql -Unhsrc facilities_assessment_test < ../nqas.sql");
    }
}
package org.nhsrc.referenceDataImport;

import org.junit.Before;
import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;

import java.io.File;

public class JSSChecklistCreator {
    private File checklistDirectory;
    private ChecklistCreator checklistCreator;

    @Before
    public void init() {
        File projectDirectory = new File("../reference-data");
        checklistDirectory = new File(projectDirectory,"jss/mp/checklists");
        checklistCreator = new ChecklistCreator();
    }

    @Test
    public void generate_MP_CHC_Checklist_SQL() throws Exception {
        createChecklist("CHC.xlsx", "CHC.sql", "Community Health Center (CHC)");
    }

    @Test
    public void generate_MP_DH_Checklist_SQL() throws Exception {
        createChecklist("DH.xlsx", "DH.sql", "District Hospital (DH)");
    }

    private void createChecklist(String checklistFile, String outputFileName, String checklistName) throws Exception {
        File chFile = new File(checklistDirectory, checklistFile);
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.set(new AssessmentTool(checklistName, "nqas"));

        checklistCreator.performImport(chFile, assessmentChecklistData);
        checklistCreator.generate(assessmentChecklistData, new File(checklistDirectory, outputFileName), true);
    }
}
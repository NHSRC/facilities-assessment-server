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
        checklistDirectory = new File(projectDirectory,"jss/jss-checklists");
        checklistCreator = new ChecklistCreator();
    }

    @Test
    public void generate_Checklist_SQL() throws Exception {
        createChecklist("Subcentre.xlsx", "SC.sql", "Subcentre (SC)");
    }

    private void createChecklist(String checklistFile, String outputFileName, String checklistName) throws Exception {
        File chFile = new File(checklistDirectory, checklistFile);
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.set(new AssessmentTool(checklistName, "nqas"));

        checklistCreator.performImport(chFile, assessmentChecklistData);
        checklistCreator.generate(assessmentChecklistData, new File(checklistDirectory, outputFileName), false);
    }
}
package org.nhsrc.referenceDataImport;

import org.junit.Before;
import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;

import java.io.File;

public class DakshataChecklistCreator {
    private File checklistsProjectDirectory;

    @Before
    public void init() {
        checklistsProjectDirectory = new File("../reference-data/dakshata");
    }

    @Test
    public void generate_Forms() throws Exception {
        AssessmentTool assessmentTool = new AssessmentTool("Dakshata", "dakshata");
        File checklistFile = new File(checklistsProjectDirectory, "Forms.xlsx");
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.set(assessmentTool);
        ChecklistCreator checklistCreator = new ChecklistCreator();
        checklistCreator.performImport(checklistFile, assessmentChecklistData);
        File outputFile = new File(checklistsProjectDirectory, String.format("output/%s", "forms.sql"));
        checklistCreator.generate(assessmentChecklistData, outputFile, false);
    }
}
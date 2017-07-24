package org.nhsrc.referenceDataImport;

import org.junit.Before;
import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;

import java.io.File;

public class JSSChecklistCreator {
    private File projectDirectory;

    @Before
    public void init() {
        projectDirectory = new File("../reference-data");
    }

    @Test
    public void generate_MP_CHC_Checklist_SQL() throws Exception {
        File inputDirectory = new File(projectDirectory,"jss/mp/checklists");
        File outputDirectory = new File(projectDirectory,"jss/mp/checklists");
        File chFile = new File(inputDirectory, "CHC.xlsx");
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.set(new AssessmentTool("Community Health Center (CHC)", "nqas"));

        ChecklistCreator checklistCreator = new ChecklistCreator();
        checklistCreator.performImport(chFile, assessmentChecklistData);
        checklistCreator.generate(assessmentChecklistData, new File(outputDirectory, "CHC.sql"), true);
    }
}
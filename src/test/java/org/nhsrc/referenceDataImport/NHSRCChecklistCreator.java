package org.nhsrc.referenceDataImport;

import org.junit.Before;
import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;

import java.io.File;

public class NHSRCChecklistCreator {
    private File checklistsProjectDirectory;

    @Before
    public void init() {
        checklistsProjectDirectory = new File("../reference-data");
    }

    @Test
    public void generateNHSRC_LAQSHYA() throws Exception {
        generate("nhsrc/laqshya/LR10Feb.xlsx", "NQAS", "LAQSHYA", "nhsrc/output/LAQSHYA-NQAS-LR.sql", true);
        generate("nhsrc/laqshya/OT10Feb.xlsx", "NQAS", "LAQSHYA", "nhsrc/output/LAQSHYA-NQAS-OT.sql", true);
    }

    private void generate(String inputFile, String assessmentToolName, String assessmentToolModeName, String outputFileName, boolean assessmentToolExists) throws Exception {
        File checklistFile = new File(checklistsProjectDirectory, inputFile);
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.set(new AssessmentTool(assessmentToolName, assessmentToolModeName));
        ChecklistCreator checklistCreator = new ChecklistCreator();
        checklistCreator.performImport(checklistFile, assessmentChecklistData);
        checklistCreator.generate(assessmentChecklistData, new File(checklistsProjectDirectory, outputFileName), assessmentToolExists);
    }
}
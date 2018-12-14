package org.nhsrc.referenceDataImport;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;

import java.io.File;

@Ignore
public class NHSRCChecklistCreator {
    private File checklistsProjectDirectory;

    @Before
    public void init() {
        checklistsProjectDirectory = new File("../reference-data");
    }

    @Test
    public void generateNHSRC_LAQSHYA() throws Exception {
        generate("nhsrc/kayakalp/DH_SDH_CHC_27_12_17.xlsx", "DH, SDH and CHC", "Kayakalp", "nhsrc/output/DH_SDH_CHC_27_12_17.sql");
        generate("nhsrc/kayakalp/PHC_27_12_17.xlsx", "PHC", "Kayakalp", "nhsrc/output/PHC_27_12_17.sql");
        generate("nhsrc/kayakalp/UPHC_APHC_27_12_17.xlsx", "UPHC and APHC", "Kayakalp", "nhsrc/output/UPHC_APHC_27_12_17.sql");
    }

    private void generate(String inputFile, String assessmentToolName, String assessmentToolModeName, String outputFileName) throws Exception {
        File checklistFile = new File(checklistsProjectDirectory, inputFile);
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.set(new AssessmentTool(assessmentToolName, assessmentToolModeName));
        ChecklistCreator checklistCreator = new ChecklistCreator();
        checklistCreator.performImport(checklistFile, assessmentChecklistData);
        checklistCreator.generate(assessmentChecklistData, new File(checklistsProjectDirectory, outputFileName), true, true);
    }
}
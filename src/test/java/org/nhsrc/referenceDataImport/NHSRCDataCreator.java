package org.nhsrc.referenceDataImport;

import org.junit.Before;
import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;

import java.io.File;

public class NHSRCDataCreator {
    private File checklistsProjectDirectory;

    @Before
    public void init() {
        checklistsProjectDirectory = new File("../checklists/nhsrc");
    }

    @Test
    public void generate_NQAS_DH() throws Exception {
        generateNQASChecklistSQL("NQAS-DH-May-2016.xlsx","District Hospital (DH)", "nqas", "NHSRC_NQAS_DH.sql");
    }

    @Test
    public void generate_NQAS_CHC() throws Exception {
        generateNQASChecklistSQL("NQAS-CHC 13-05-2016.xlsx","Community Health Center (CHC)", "nqas", "NHSRC_NQAS_CHC.sql");
    }

    @Test
    public void generate_NQAS_PHC() throws Exception {
        generateNQASChecklistSQL("NQAS-PHC.xlsx","Primary Health Center (PHC)", "nqas", "NHSRC_NQAS_PHC.sql");
    }

    @Test
    public void generate_NQAS_UPHC() throws Exception {
        generateNQASChecklistSQL("NQAS-Urban PHC 26-Feb-16.xlsx","Urban Primary Health Center (UPHC)", "nqas", "NHSRC_NQAS_UPHC.sql");
    }

    @Test
    public void generate_KK_DH_SDH_CHC() throws Exception {
        generateNQASChecklistSQL("Kayakalp-DH-SDH-CHC-28-July-2016.xlsx","DH, SDH and CHC", "Kayakalp", "NHSRC_KK_DH_SDH_CHC.sql");
    }

    @Test
    public void generate_KK_PHC() throws Exception {
        generateNQASChecklistSQL("Kayakalp-PHC-23rd-August-2016.xlsx","PHC", "Kayakalp", "NHSRC_KK_PHC.sql");
    }

    @Test
    public void generate_KK_UPHC_APHC() throws Exception {
        generateNQASChecklistSQL("Kayakalp-UPHC-APHC-28-July-2016.xlsx","UPHC and APHC", "Kayakalp", "NHSRC_KK_UPHC_APHC.sql");
    }

    private void generateNQASChecklistSQL(String inputFileName, String assessmentToolName, String assessmentToolMode, String outputFileName) throws Exception {
        AssessmentTool assessmentTool = new AssessmentTool(assessmentToolName, assessmentToolMode);
        File checklistFile = new File(checklistsProjectDirectory, String.format("%s/%s", assessmentToolMode, inputFileName));
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.set(assessmentTool);
        ChecklistCreator checklistCreator = new ChecklistCreator();
        checklistCreator.performImport(checklistFile, 0, assessmentChecklistData);
        File outputFile = new File(checklistsProjectDirectory, String.format("output/%s", outputFileName));
        checklistCreator.generate(assessmentChecklistData, outputFile, true);
    }
}
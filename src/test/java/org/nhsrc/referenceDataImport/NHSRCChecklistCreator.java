package org.nhsrc.referenceDataImport;

import org.junit.Before;
import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class NHSRCChecklistCreator {
    private File checklistsProjectDirectory;

    @Before
    public void init() {
        checklistsProjectDirectory = new File("../checklists");
    }

    @Test
    public void generateNHSRC_KK_DH_SDH_CHC() throws Exception {
        File checklistFile = new File(checklistsProjectDirectory, "nhsrc/DH,SDH & CHC Kayakalp 28 July 2016.xlsx");
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.set(new AssessmentTool("Kayakalp", "Kayakalp"));
        ChecklistCreator checklistCreator = new ChecklistCreator();
        checklistCreator.performImport(checklistFile, assessmentChecklistData);
        checklistCreator.generate(assessmentChecklistData, new File(checklistsProjectDirectory,"NHSRC_KK_DH_SDH_CHC.sql"), true);
    }
}
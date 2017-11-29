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
        checklistsProjectDirectory = new File("../reference-data");
    }

    @Test
    public void generateNHSRC_LAQSHYA() throws Exception {
        File checklistFile = new File(checklistsProjectDirectory, "nhsrc/laqshya/LAQSHYA-DH-May-2016.xlsx");
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.set(new AssessmentTool("LAQSHYA", "LAQSHYA"));
        ChecklistCreator checklistCreator = new ChecklistCreator();
        checklistCreator.performImport(checklistFile, assessmentChecklistData);
        checklistCreator.generate(assessmentChecklistData, new File(checklistsProjectDirectory,"nhsrc/output/LAQSHYA.sql"), false);
    }
}
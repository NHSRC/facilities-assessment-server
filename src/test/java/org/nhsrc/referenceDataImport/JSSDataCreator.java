package org.nhsrc.referenceDataImport;

import org.junit.Before;
import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class JSSDataCreator {
    private File checklistsProjectDirectory;

    @Before
    public void init() {
        checklistsProjectDirectory = new File("../checklists");
    }

    @Test
    public void generateCGSQL() throws Exception {
        String jssCGInputDir = "../checklists/jss/cg";
        File dhInputDirectory = new File(jssCGInputDir,"CG-NQAS-DH-English");
        ChecklistCreator checklistCreator = new ChecklistCreator();
        List<File> files = Arrays.asList(dhInputDirectory.listFiles((dir, name) -> !(name.contains("DS_Store") || name.contains(".sql"))));
        List<File> dhFiles = files.stream().sorted(Comparator.comparing(this::getFileNumber)).collect(Collectors.toList());

//        AssessmentChecklistData nqasCGDHData = new AssessmentChecklistData();
//        dhFiles.forEach(dhFile -> {
//            try {
//                checklistCreator.performImport("District Hospital (DH)", "nqas", dhFile, 0, nqasData);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        checklistCreator.generate(nqasCGDHData, new File(dhInputDirectory, "output.sql"));

//        File chFile = new File(jssCGInputDir, "CG-NQAS-CHC-English.xlsx");
//        AssessmentChecklistData nqasCGCHCData = new AssessmentChecklistData();
//        checklistCreator.performImport("Community Hospital (CH)", "nqas", chFile, 0, nqasCGCHCData);
//        checklistCreator.generate(nqasCGCHCData, new File(jssCGInputDir, "output.sql"));

//        File chFile = new File(jssCGInputDir, "CG-NQAS-CHC-BSU-English.xlsx");
//        AssessmentChecklistData nqasCGCHCBSUData = new AssessmentChecklistData();
//        checklistCreator.performImport("Community Hospital (CH)", "nqas", chFile, 0, nqasCGCHCBSUData);
//        checklistCreator.generate(nqasCGCHCBSUData, new File(jssCGInputDir, "output-bsu.sql"), true);

        File chFile = new File(jssCGInputDir, "CG-NQAS-CHC-BSU-AOCInputs-English.xlsx");
        AssessmentChecklistData nqasCGCHCBSUInputsData = new AssessmentChecklistData();
        nqasCGCHCBSUInputsData.set(new AssessmentTool("Community Hospital (CH)", "nqas"));
        checklistCreator.performImport(chFile, nqasCGCHCBSUInputsData);
        checklistCreator.generate(nqasCGCHCBSUInputsData, new File(jssCGInputDir, "output-bsu-inputs.sql"), true);
    }

    @Test
    public void generate_MP_CHC_Checklist_SQL() throws Exception {
        File inputDirectory = new File(checklistsProjectDirectory,"jss/mp");
        File chFile = new File(inputDirectory, "CHC_CHC.xlsx");
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        assessmentChecklistData.set(new AssessmentTool("Community Hospital (CH)", "nqas"));

        ChecklistCreator checklistCreator = new ChecklistCreator();
        checklistCreator.performImport(chFile, assessmentChecklistData);
        checklistCreator.generate(assessmentChecklistData, new File(checklistsProjectDirectory, "CHC_CHC.sql"), true);
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

    private Integer getFileNumber(File file) {
        StringTokenizer stringTokenizer = new StringTokenizer(file.getName(), ".");
        String number = stringTokenizer.nextToken();
        return Integer.parseInt(number);
    }

    private String getOutputFileName(File file) {
        String outputFileName = file.getName().replaceFirst("[.][^.]+$", "");
        StringTokenizer stringTokenizer = new StringTokenizer(outputFileName, ".");
        stringTokenizer.nextToken();
        return stringTokenizer.nextToken() + ".sql";
    }
}
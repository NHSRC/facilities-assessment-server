package org.nhsrc.referenceDataImport;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.stream.Collectors;

public class JSSDataCreator {
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

        File chFile = new File(jssCGInputDir, "CG-NQAS-CHC-BSU-English.xlsx");
        AssessmentChecklistData nqasCGCHCBSUData = new AssessmentChecklistData();
        checklistCreator.performImport("Community Hospital (CH)", "nqas", chFile, 0, nqasCGCHCBSUData);
        checklistCreator.generate(nqasCGCHCBSUData, new File(jssCGInputDir, "output-bsu.sql"), true);
    }

    @Test
    public void generateMPSQL() {
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
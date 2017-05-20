package org.nhsrc.referenceDataImport;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.stream.Collectors;

public class JSSDataCreator {
    @Test
    public void generateCGSQL() throws Exception {
        File dhInputDirectory = new File("../checklists/jss/cg/CG-NQAS-DH-English");
        ChecklistCreator checklistCreator = new ChecklistCreator();
        List<File> files = Arrays.asList(dhInputDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !(name.contains("DS_Store") || name.contains(".sql"));
            }
        }));
        List<File> dhFiles = files.stream().sorted(Comparator.comparing(this::getFileNumber)).collect(Collectors.toList());

        AssessmentChecklistData nqasData = new AssessmentChecklistData();
        File chFile = new File("../checklists/jss/cg/CG-NQAS-CHC-English.xlsx");
        dhFiles.forEach(dhFile -> {
            try {
                checklistCreator.performImport("District Hospital (DH)", "nqas", dhFile, 0, nqasData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        checklistCreator.generate(nqasData, new File(dhInputDirectory, "output.sql"));
//        checklistCreator.create("nqas", chFile, 0, new File(getOutputFileName(chFile)));
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
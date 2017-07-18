package org.nhsrc.excel;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AssessmentsDirectory {
    private final File assessmentsDirectory;

    public AssessmentsDirectory(String rootPath) {
        assessmentsDirectory = new File(rootPath);
    }

    public List<AssessmentFile> getAssessmentFiles() {
        List<AssessmentFile> assessmentFiles = new ArrayList<>();

        File[] files = assessmentsDirectory.listFiles(ExcelDirectory.FilenameFilter);
        Arrays.stream(files).forEach(assessmentFile -> {
            assessmentFiles.add(new AssessmentFile(assessmentFile));
        });

        return assessmentFiles;
    }

    public File toDirectory() {
        return this.assessmentsDirectory;
    }
}
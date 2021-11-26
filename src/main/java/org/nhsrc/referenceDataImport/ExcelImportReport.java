package org.nhsrc.referenceDataImport;

import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelImportReport {
    private Map<String, List<String>> errors;
    private Map<String, Integer> checkpoints;
    private final List<String> fileErrors = new ArrayList<>();
    private final GunakExcelFile gunakExcelFile;

    public ExcelImportReport(GunakExcelFile gunakExcelFile) {
        this.gunakExcelFile = gunakExcelFile;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public Map<String, Integer> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Map<String, Integer> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public void addFileLevelError(String message) {
        this.fileErrors.add(message);
    }

    public boolean hasErrors() {
        return fileErrors.size() > 0 || errors.values().stream().map(List::size).reduce(Integer::sum).get() > 0;
    }

    public Context getContext() {
        Context context = new Context();
        context.setVariable("assessmentToolName", gunakExcelFile.getAssessmentTool().getName());
        context.setVariable("allChecklistErrors", errors);
        context.setVariable("fileLevelErrors", fileErrors);
        return context;
    }
}

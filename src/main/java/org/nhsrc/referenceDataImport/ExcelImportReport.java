package org.nhsrc.referenceDataImport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelImportReport {
    private Map<String, List<String>> errors;
    private Map<String, Integer> checkpoints;

    public Map<String, List<String>> getErrors() {
        return errors;
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
}

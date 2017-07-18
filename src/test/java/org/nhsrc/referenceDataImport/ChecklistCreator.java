package org.nhsrc.referenceDataImport;

import java.io.File;
import java.io.IOException;

public class ChecklistCreator {
    public void performImport(File inputExcelFile, AssessmentChecklistData data) throws Exception {
        ExcelImporter excelImporter = new ExcelImporter(data);
        excelImporter.importFile(inputExcelFile, data.getAssessmentTool(), 0, false, null);
    }

    public void generate(AssessmentChecklistData data, File outputFile, boolean assessmentToolExists) throws IOException {
        SQLGenerator sqlGenerator = new SQLGenerator();
        sqlGenerator.generateChecklist(data, outputFile, assessmentToolExists);
    }
}
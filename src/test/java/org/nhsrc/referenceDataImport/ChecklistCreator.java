package org.nhsrc.referenceDataImport;

import java.io.File;
import java.io.IOException;

public class ChecklistCreator {
    public void performImport(File inputExcelFile, int startingSheet, AssessmentChecklistData data) throws Exception {
        ExcelImporter excelImporter = new ExcelImporter(data);
        excelImporter.importFile(inputExcelFile, data.getAssessmentTool(), startingSheet, false, null);
    }

    public void generate(AssessmentChecklistData data, File outputFile, boolean assessmentToolExists) throws IOException {
        SQLGenerator sqlGenerator = new SQLGenerator();
        StringBuffer stringBuffer = new StringBuffer();
        sqlGenerator.generate(data, outputFile, stringBuffer, assessmentToolExists);
    }
}
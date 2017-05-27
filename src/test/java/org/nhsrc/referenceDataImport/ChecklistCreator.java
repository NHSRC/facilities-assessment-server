package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.AssessmentTool;

import java.io.File;
import java.io.IOException;

public class ChecklistCreator {
    public void performImport(String assessmentToolName, String assessmentToolMode, File inputExcelFile, int startingSheet, AssessmentChecklistData data) throws Exception {
        AssessmentTool assessmentTool = new AssessmentTool();
        assessmentTool.setName(assessmentToolName);
        assessmentTool.setMode(assessmentToolMode);
        data.set(assessmentTool);
        ExcelImporter excelImporter = new ExcelImporter(data);
        excelImporter.importFile(inputExcelFile, assessmentTool, startingSheet);
    }

    public void generate(AssessmentChecklistData data, File outputFile, boolean assessmentToolExists) throws IOException {
        SQLGenerator sqlGenerator = new SQLGenerator();
        StringBuffer stringBuffer = new StringBuffer();
        sqlGenerator.generate(data, outputFile, stringBuffer, assessmentToolExists);
    }
}
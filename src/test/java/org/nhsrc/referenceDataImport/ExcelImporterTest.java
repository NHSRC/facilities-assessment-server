package org.nhsrc.referenceDataImport;

import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.State;

import java.io.File;

public class ExcelImporterTest {
    @Test
    public void importNQAS_Emergency() throws Exception {
        String dirPath = "/Users/mihir/projects/nhsrc/pilot";
        File file = new File(dirPath, "NQAS_DH.xlsx");
        SQLGenerator sqlGenerator = new SQLGenerator();

        AssessmentChecklistData nqasData = new AssessmentChecklistData();
        AssessmentTool assessmentTool = new AssessmentTool();
        assessmentTool.setMode("nqas");
        assessmentTool.setName("District Hospital (DH)");
        nqasData.set(assessmentTool);

        State state = new State();
        state.setName("Karnataka");

        nqasData.set(state);

        ExcelImporter excelImporter = new ExcelImporter(nqasData);
        excelImporter.importFile(file, assessmentTool, state, 1);
        StringBuffer stringBuffer = new StringBuffer();
        sqlGenerator.generateState(nqasData, stringBuffer);
        sqlGenerator.generate(nqasData, new File(dirPath, "nqas.sql"), stringBuffer);

        AssessmentChecklistData kayakalpData = new AssessmentChecklistData();
        assessmentTool = new AssessmentTool();
        assessmentTool.setMode("kayakalp");
        assessmentTool.setName("Kayakalp");
        kayakalpData.set(assessmentTool);
        kayakalpData.set(state);

        file = new File(dirPath, "Kayakalp_DH.xlsx");
        excelImporter = new ExcelImporter(kayakalpData);
        stringBuffer = new StringBuffer();
        excelImporter.importFile(file, assessmentTool, state, 0);
        sqlGenerator.generate(kayakalpData, new File(dirPath, "kayakalp.sql"), stringBuffer);

//        Runtime.getRuntime().exec("psql -Unhsrc facilities_assessment_test < ../nqas.sql");
    }
}
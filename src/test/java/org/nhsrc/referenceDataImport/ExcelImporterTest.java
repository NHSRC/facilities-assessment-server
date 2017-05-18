package org.nhsrc.referenceDataImport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.State;
import org.nhsrc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExcelImporterTest {
    @Test
    public void importNQAS_Emergency() throws Exception {
        String dirPath = "/Users/vsingh/Downloads";
        File file = new File(dirPath, "Chececklist_DH_New_Revised May 2016.xlsx");
        AssessmentChecklistData data = new AssessmentChecklistData();

        AssessmentTool assessmentTool = new AssessmentTool();
        assessmentTool.setMode("nqas");
        assessmentTool.setName("District Hospital (DH)");
        data.set(assessmentTool);

        State state = new State();
        state.setName("Karnataka");
        data.set(state);

        ExcelImporter excelImporter = new ExcelImporter(data);
        excelImporter.importFile(file, assessmentTool, state);

        SQLGenerator sqlGenerator = new SQLGenerator();
        sqlGenerator.generate(data, new File(dirPath, "nqas.sql"));

//        Runtime.getRuntime().exec("psql -Unhsrc facilities_assessment_test < ../nqas.sql");
    }
}
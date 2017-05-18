package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nhsrc.domain.*;
import org.nhsrc.repository.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelImporter {
    private AssessmentChecklistData data;

    public ExcelImporter(AssessmentChecklistData data) {
        this.data = data;
    }

    public void importFile(File file, AssessmentTool assessmentTool, State state) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        try {
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 1; i < numberOfSheets; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                this.sheetImport(sheet, state, assessmentTool);
                System.out.println("COMPLETED SHEET");
            }
        } finally {
            workbook.close();
            inputStream.close();
        }
    }

    public Checklist sheetImport(XSSFSheet sheet, State state, AssessmentTool assessmentTool) throws Exception {
        Department department = makeDepartment(sheet.getSheetName());
        data.addDepartment(department);

        final Checklist checklist = new Checklist();
        checklist.setDepartment(department);
        checklist.setName(department.getName());
        checklist.setAssessmentTool(assessmentTool);
        data.addChecklist(checklist);

        SheetRowImporter sheetImporter = new SheetRowImporter(data);

        int i = 1;
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
//            System.out.println(sheet.getSheetName() + ", Processing row " + i++);
            sheetImporter.importRow(iterator.next(), state, checklist);
        }

        System.out.println(checklist.toSummary());
        return checklist;
    }

    private Department makeDepartment(String name) {
        Department department = new Department();
        department.setName(name);
        return department;
    }
}

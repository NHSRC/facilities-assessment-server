package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nhsrc.domain.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class ExcelImporter {
    private AssessmentChecklistData data;

    public ExcelImporter(AssessmentChecklistData data) {
        this.data = data;
    }

    public void importFile(File file, AssessmentTool assessmentTool, int startingSheet) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        try {
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = startingSheet; i < numberOfSheets; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                this.sheetImport(sheet, assessmentTool);
                System.out.println("COMPLETED SHEET: " + sheet.getSheetName());
            }
        } finally {
            workbook.close();
            inputStream.close();
        }
    }

    public Checklist sheetImport(XSSFSheet sheet, AssessmentTool assessmentTool) throws Exception {
        Department department = makeDepartment(sheet.getSheetName().trim());
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
            sheetImporter.importRow(iterator.next(), checklist);
        }

//        System.out.println(checklist.toSummary());
        return checklist;
    }

    private Department makeDepartment(String name) {
        Department department = new Department();
        department.setName(name);
        return department;
    }
}

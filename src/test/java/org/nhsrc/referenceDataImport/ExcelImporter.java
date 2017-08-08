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

    public void importFile(File file, AssessmentTool assessmentTool, int startingSheet, int numberOfSheetsToImport, boolean hasScores, FacilityAssessment facilityAssessment) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        try {
            int numberOfSheets = numberOfSheetsToImport == -1 ? workbook.getNumberOfSheets() : numberOfSheetsToImport;
            for (int i = startingSheet; i < numberOfSheets; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                System.out.println("READING SHEET: " + sheet.getSheetName());
                this.sheetImport(sheet, assessmentTool, hasScores, facilityAssessment);
                System.out.println("COMPLETED SHEET: " + sheet.getSheetName());
            }
        } finally {
            workbook.close();
            inputStream.close();
        }
    }

    public Checklist sheetImport(XSSFSheet sheet, AssessmentTool assessmentTool, boolean hasScores, FacilityAssessment facilityAssessment) throws Exception {
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
            boolean completed = sheetImporter.importRow(iterator.next(), checklist, hasScores, facilityAssessment);
            if (completed) {
                System.out.println(String.format("Sheet completed at line number:%d on encountering score row", i));
                break;
            }
            i++;
        }
        if (checklist.getAreasOfConcern().size() == 0) {
            System.err.println(String.format("[ERROR] No area of concern were created for the sheet=%s. Ensure that the sheet is right. That is it has area of concerns and standards defined correctly.", checklist.getName()));
        } else if (checklist.getCheckpoints().size() == 0) {
            System.err.println(String.format("[ERROR] No checkpoints were created for the sheet=%s. Ensure that the sheet is right. That is it has area of concerns and standards defined correctly.", checklist.getName()));
        }
        return checklist;
    }

    private Department makeDepartment(String name) {
        Department department = new Department();
        if (name.contains(".") || name.contains("-") || name.contains("_")) {
            System.err.println(String.format("[ERROR] Department name=%s doesn't look right. Department name should not contain . - _ characters", name));
        }
        department.setName(name);
        return department;
    }
}

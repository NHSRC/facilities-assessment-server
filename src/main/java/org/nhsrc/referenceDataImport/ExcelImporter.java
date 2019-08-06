package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.Department;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.web.FacilityAssessmentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

public class ExcelImporter {
    private static Logger logger = LoggerFactory.getLogger(ExcelImporter.class);
    private AssessmentChecklistData data;
    private static String[] RESERVED_SHEET_NAMES = {"Compatibility Report", "Department Wise"};

    public ExcelImporter(AssessmentChecklistData data) {
        this.data = data;
    }

    public void importFile(InputStream inputStream, AssessmentTool assessmentTool, boolean hasScores, FacilityAssessment facilityAssessment) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        try {
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                logger.info("READING SHEET: " + sheet.getSheetName());
                this.sheetImport(sheet, assessmentTool, hasScores, facilityAssessment);
                logger.info("COMPLETED SHEET: " + sheet.getSheetName());
            }
        } finally {
            workbook.close();
            inputStream.close();
        }
    }

    public void importFile(File file, AssessmentTool assessmentTool, boolean hasScores, FacilityAssessment facilityAssessment) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);
        this.importFile(inputStream, assessmentTool, hasScores, facilityAssessment);
    }

    private void sheetImport(XSSFSheet sheet, AssessmentTool assessmentTool, boolean hasScores, FacilityAssessment facilityAssessment) {
        String sheetName = sheet.getSheetName().trim();
        if (Arrays.stream(RESERVED_SHEET_NAMES).anyMatch(s -> s.equalsIgnoreCase(sheetName))) {
            return;
        }

        Department department = makeDepartment(sheetName);
        data.addDepartment(department);

        final Checklist checklist = new Checklist();
        checklist.setDepartment(department);
        checklist.setName(sheetName);
        checklist.addAssessmentTool(assessmentTool);
        data.addChecklist(checklist);

        SheetRowImporter sheetImporter = new SheetRowImporter(data);

        int i = 1;
        for (Row cells : sheet) {
            boolean completed = sheetImporter.importRow(cells, checklist, hasScores, facilityAssessment);
            if (completed) {
                logger.info(String.format("Sheet completed at line number:%d on encountering score row", i));
                break;
            }
            i++;
        }
        if (checklist.getAreasOfConcern().size() == 0) {
            logger.error(String.format("[ERROR] No area of concern were created for the sheet=%s. Ensure that the sheet is right. That is it has area of concerns and standards defined correctly.", checklist.getName()));
        } else if (checklist.getCheckpoints().size() == 0) {
            logger.error(String.format("[ERROR] No checkpoints were created for the sheet=%s. Ensure that the sheet is right. That is it has area of concerns and standards defined correctly.", checklist.getName()));
        }
    }

    private Department makeDepartment(String name) {
        Department department = new Department();
        if (name.contains(".") || name.contains("-") || name.contains("_")) {
            logger.error(String.format("[ERROR] Department name=%s doesn't look right. Department name should not contain . - _ characters", name));
        }
        department.setName(name);
        return department;
    }
}

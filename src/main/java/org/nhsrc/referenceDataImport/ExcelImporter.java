package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nhsrc.domain.Checklist;
import org.nhsrc.repository.theme.ThemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.*;

public class ExcelImporter {
    private static final Logger logger = LoggerFactory.getLogger(ExcelImporter.class);
    private static final String[] RESERVED_SHEET_NAMES = {"Compatibility Report", "Department Wise"};

    public void importFile(GunakExcelFile gunakExcelFile, ThemeRepository themeRepository, InputStream inputStream) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Map<String, List<String>> errors = new HashMap<>();
        Map<String, Integer> checkpointsCount = new HashMap<>();
        ExcelImportReport excelImportReport = new ExcelImportReport(gunakExcelFile);
        try {
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                logger.info("READING SHEET: " + sheet.getSheetName());
                SheetImporter sheetImporter = new SheetImporter(gunakExcelFile, themeRepository);
                this.sheetImport(sheet, gunakExcelFile, sheetImporter);
                errors.put(sheet.getSheetName(), sheetImporter.getErrors());
                checkpointsCount.put(sheet.getSheetName(), sheetImporter.getTotalCheckpoints());
                logger.info("COMPLETED SHEET: " + sheet.getSheetName());
            }
        } finally {
            workbook.close();
            inputStream.close();
        }
        excelImportReport.setErrors(errors);
        excelImportReport.setCheckpoints(checkpointsCount);
        gunakExcelFile.setReport(excelImportReport);
    }

    private void sheetImport(XSSFSheet sheet, GunakExcelFile gunakExcelFile, SheetImporter sheetImporter) {
        String sheetName = sheet.getSheetName().trim();
        if (Arrays.stream(RESERVED_SHEET_NAMES).anyMatch(s -> s.equalsIgnoreCase(sheetName))) {
            return;
        }

        Checklist checklist = new Checklist();
        checklist.setName(sheetName);
        gunakExcelFile.addChecklist(checklist);
        checklist.addAssessmentTool(gunakExcelFile.getAssessmentTool());

        int i = 1;
        for (Row cells : sheet) {
            boolean completed = sheetImporter.importRow(cells, checklist);
            if (completed) {
                logger.info(String.format("Sheet completed at line number:%d on encountering score row", i));
                break;
            }
            i++;
        }
    }
}

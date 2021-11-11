package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Arrays;

public class ExcelImporter {
    private static final Logger logger = LoggerFactory.getLogger(ExcelImporter.class);
    private GunakExcelFile data;
    private static final String[] RESERVED_SHEET_NAMES = {"Compatibility Report", "Department Wise"};

    public ExcelImporter(GunakExcelFile data) {
        this.data = data;
    }

    public void importFile(InputStream inputStream) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        try {
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                logger.info("READING SHEET: " + sheet.getSheetName());
                this.sheetImport(sheet);
                logger.info("COMPLETED SHEET: " + sheet.getSheetName());
            }
        } finally {
            workbook.close();
            inputStream.close();
        }
    }

    private void sheetImport(XSSFSheet sheet) {
        String sheetName = sheet.getSheetName().trim();
        if (Arrays.stream(RESERVED_SHEET_NAMES).anyMatch(s -> s.equalsIgnoreCase(sheetName))) {
            return;
        }

        final Checklist checklist = new Checklist();
        checklist.setName(sheetName);
        data.addChecklist(checklist);

        SheetRowImporter sheetImporter = new SheetRowImporter(data);

        int i = 1;
        for (Row cells : sheet) {
            boolean completed = sheetImporter.importRow(cells, checklist);
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
}

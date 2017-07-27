package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StandardShortNameImporter {

    public static final String SHORT_NAME_UPDATE_QUERY = "UPDATE standard SET short_name='%s' WHERE reference='%s' AND assessment_tool_id IN (SELECT id FROM assessment_tool WHERE mode='nqas');";
    private File standardShortNameInputFile;
    private String standardShortNameOutputFilePath;

    @Before
    public void setUp() throws Exception {
        standardShortNameInputFile = new File("../reference-data/nhsrc/nqas/standards_short_names.xlsx");
        standardShortNameOutputFilePath = "../reference-data/nhsrc/output/standards_short_names.sql";
    }

    @Test
    public void importStandardDataNHSRC() throws Exception {
        this.importShortNames(standardShortNameInputFile, standardShortNameOutputFilePath);
    }

    public void importShortNames(File standardShortNameInputFile, String standardShortNameOutputFilePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(standardShortNameInputFile);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        String updateSQL = "";
        try {
            XSSFSheet mainSheet = workbook.getSheetAt(0);
            List<Row> rows = new ArrayList<>();
            mainSheet.iterator().forEachRemaining(rows::add);
            updateSQL = rows.parallelStream().map((row) -> String.format(SHORT_NAME_UPDATE_QUERY, row.getCell(1).getStringCellValue(), row.getCell(0).getStringCellValue())).collect(Collectors.joining("\n"));
        } finally {
            workbook.close();
            inputStream.close();
        }
        Files.write(Paths.get(standardShortNameOutputFilePath), updateSQL.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}

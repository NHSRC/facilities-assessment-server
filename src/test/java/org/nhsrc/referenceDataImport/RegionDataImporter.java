package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegionDataImporter {
    @Test
    public void generateJSSRegionDataSQL() throws Exception {
        File regionInputDir = new File("../reference-data/jss/regions");
        generateRegionData(regionInputDir, "../reference-data/jss/regions/regionData.sql");
    }

    @Test
    public void generateNHSRCRegionData() throws IOException {
        File regionInputDir = new File("../reference-data/nhsrc/regions/ANIslands.xlsx");
        generateRegionData(regionInputDir, "../reference-data/nhsrc/regions/andaman-nicobar.sql");
    }

    private void generateRegionData(File inputFile, String outputFile) throws IOException {
        RegionData regionData = new RegionData();
        String regionDataSQL = "";

        FileInputStream inputStream = new FileInputStream(inputFile);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        try {
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                List<Row> rows = new ArrayList<Row>();
                sheet.iterator().forEachRemaining(rows::add);
                rows.remove(0);
                rows.stream().map(StateCreator::create).forEach(regionData::addState);
                regionDataSQL = regionDataSQL.concat(regionData.toSQL());
            }
        } finally {
            workbook.close();
            inputStream.close();
        }
        Files.write(Paths.get(outputFile), regionDataSQL.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}

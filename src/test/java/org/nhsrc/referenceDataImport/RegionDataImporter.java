package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegionDataImporter {

    private File regionInputDir = new File("../reference-data/jss/regions");

    @Test
    public void generateRegionDataSQL() throws Exception {
        RegionData regionData = new RegionData();
        List<File> regionFiles = Arrays.asList(regionInputDir.listFiles((dir, name) -> name.endsWith(".xlsx") && !name.startsWith("~")));
        for (File regionFile : regionFiles) {
            System.out.println("Processing " + regionFile.getName());
            FileInputStream inputStream = new FileInputStream(regionFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            try {
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    List<Row> rows = new ArrayList<Row>();
                    sheet.iterator().forEachRemaining(rows::add);
                    rows.remove(0);
                    rows.stream().filter(StateCreator::facilityTypeFilter).map(StateCreator::create).forEach(regionData::addState);
                    System.out.println(regionData);
                }
            } finally {
                workbook.close();
                inputStream.close();
            }
        }
    }
}

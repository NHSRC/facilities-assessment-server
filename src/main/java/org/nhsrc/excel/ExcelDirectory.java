package org.nhsrc.excel;

import java.io.FilenameFilter;

public class ExcelDirectory {
    public static final FilenameFilter FilenameFilter = (dir, name) -> name.endsWith(".xlsx");
}
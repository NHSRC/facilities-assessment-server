package org.nhsrc.config.excelMetaDataError;

import java.util.HashMap;
import java.util.Map;

public class AssessmentExcelMetaDataErrors {
    private static Map<String, String> stringHashMap = new HashMap<>();

    static {
        stringHashMap = new HashMap<>();
        stringHashMap.put("ME 18.3", "ME E18.3");
        stringHashMap.put("ME 18.5", "ME E18.5");
        stringHashMap.put("ME 18.6", "ME E18.6");
        stringHashMap.put("ME 18.7", "ME E18.7");
        stringHashMap.put("ME 18.10", "ME E18.10");

        stringHashMap.put("ME 19.4", "ME E19.4");
        stringHashMap.put("Standards G10", "Standard G10");
    }

    public static String getRealString(String foundString) {
        if (stringHashMap.containsKey(foundString))
            return stringHashMap.get(foundString);
        return foundString;
    }
}
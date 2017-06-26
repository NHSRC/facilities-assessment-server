package org.nhsrc.assessmentImport;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.FacilityType;

import java.util.HashMap;
import java.util.Map;

public class ShortNames {
    private static Map<String, AssessmentTool> assessmentTools = new HashMap<>();
    private static Map<String, FacilityType> facilityTypes = new HashMap<>();
    static {
        assessmentTools.put("DH", new AssessmentTool("District Hospital (DH)", "nqas"));
        assessmentTools.put("CH", new AssessmentTool("Community Hospital (CH)", "nqas"));
        assessmentTools.put("PHC", new AssessmentTool("Primary Health Center (PHC)", "nqas"));
        assessmentTools.put("UPHC", new AssessmentTool("Urban Primary Health Center (PHC)", "nqas"));
        assessmentTools.put("Kayakalp", new AssessmentTool("Kayakalp", "Kayakalp"));
    }

    static {
        facilityTypes.put("CHC", new FacilityType("Community Health Center"));
        facilityTypes.put("PHC", new FacilityType("Primary Health Center"));
        facilityTypes.put("DH", new FacilityType("District Hospital"));
    }

    public static FacilityType getFacilityType(String shortName) {
        return facilityTypes.get(shortName);
    }

    public static AssessmentTool getAssessmentTools(String shortName) {
        return assessmentTools.get(shortName);
    }
}
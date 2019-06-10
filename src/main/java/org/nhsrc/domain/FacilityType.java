package org.nhsrc.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "facility_type")
public class FacilityType extends AbstractEntity {
    private static FacilityType DH = new FacilityType("District Hospital");
    private static FacilityType PHC = new FacilityType("Primary Health Center");
    private static FacilityType CHC = new FacilityType("Community Health Center");
    private static FacilityType UPHC = new FacilityType("Urban Primary Health Center");
    private static FacilityType SDH = new FacilityType("Sub-Divisional/Taluka Hospital");
    private static FacilityType MC = new FacilityType("Medical College");

    public static Map<String, FacilityType> COMMON_FACILITY_TYPE_NAMES;

    static {
        COMMON_FACILITY_TYPE_NAMES = new HashMap<>();
        COMMON_FACILITY_TYPE_NAMES.put("CHC", CHC);
        COMMON_FACILITY_TYPE_NAMES.put("CHC-24x7", CHC);
        COMMON_FACILITY_TYPE_NAMES.put("Community Health Center", CHC);
        COMMON_FACILITY_TYPE_NAMES.put("DH", DH);
        COMMON_FACILITY_TYPE_NAMES.put("District Hospital", DH);
        COMMON_FACILITY_TYPE_NAMES.put("Medical Colleges Hospital", MC);
        COMMON_FACILITY_TYPE_NAMES.put("PHC", PHC);
        COMMON_FACILITY_TYPE_NAMES.put("SDH", SDH);
        COMMON_FACILITY_TYPE_NAMES.put("SDistrict Hospital", SDH);
        COMMON_FACILITY_TYPE_NAMES.put("SDistrict Hospital-24x7", SDH);
        COMMON_FACILITY_TYPE_NAMES.put("Sub-District Hospital", SDH);
        COMMON_FACILITY_TYPE_NAMES.put("Taluka Hospital", SDH);
        COMMON_FACILITY_TYPE_NAMES.put("Taluka Hospitals", SDH);
        COMMON_FACILITY_TYPE_NAMES.put("Urban Health Center", UPHC);
    }

    private static String[] nameQualifiers = {"Sub-District Hospital", "DISTRICT HOSPITAL", "DMO", "MCH", "GOVT", "Community Health Centre", "District Women Hospital", "DWH", "DCH", "Area Hospital", "DH", "CHC", "BPHC", "(PHC)", "PHC", "UFWC", "COMMUNITY HEALTH CENTER", "SDCH", "(FRU)", "FRU", "RH", "SDH", "DMCH", "MCH", "GH"};

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @JsonIgnore
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacilityType() {
    }

    public FacilityType(String name) {
        this.name = name;
    }
}

package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;

public class FacilityRow {
    private String facilityName;
    private String facilityType;
    private String stateName;
    private String districtName;

    public FacilityRow(Row facilityRow) {
        stateName = facilityRow.getCell(0).getStringCellValue();
        districtName = facilityRow.getCell(1).getStringCellValue();
        facilityType = facilityRow.getCell(2).getStringCellValue();
        facilityName = facilityRow.getCell(3).getStringCellValue();
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public String toString() {
        return "FacilityRow{" +
                "facilityName='" + facilityName + '\'' +
                ", facilityType='" + facilityType + '\'' +
                ", stateName='" + stateName + '\'' +
                ", districtName='" + districtName + '\'' +
                '}';
    }
}

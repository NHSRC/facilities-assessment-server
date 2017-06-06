package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;

public class FacilityRow {
    private Integer facilityId;
    private String facilityName;
    private String facilityType;
    private String stateName;
    private String districtName;

    public FacilityRow(Row facilityRow) {
        facilityId = (int) facilityRow.getCell(0).getNumericCellValue();
        facilityName = facilityRow.getCell(1).getStringCellValue();
        facilityType = facilityRow.getCell(2).getStringCellValue();
        stateName = facilityRow.getCell(4).getStringCellValue();
        districtName = facilityRow.getCell(5).getStringCellValue();
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
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
}

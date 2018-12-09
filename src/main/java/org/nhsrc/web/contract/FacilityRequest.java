package org.nhsrc.web.contract;

public class FacilityRequest {
    private String uuid;
    private Boolean inactive;
    private String name;
    private String facilityTypeUUID;
    private int facilityTypeId;
    private String districtUUID;
    private int districtId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacilityTypeUUID() {
        return facilityTypeUUID;
    }

    public void setFacilityTypeUUID(String facilityTypeUUID) {
        this.facilityTypeUUID = facilityTypeUUID;
    }

    public String getDistrictUUID() {
        return districtUUID;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public void setDistrictUUID(String districtUUID) {
        this.districtUUID = districtUUID;
    }

    public int getFacilityTypeId() {
        return facilityTypeId;
    }

    public void setFacilityTypeId(int facilityTypeId) {
        this.facilityTypeId = facilityTypeId;
    }
}
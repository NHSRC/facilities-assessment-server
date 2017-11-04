package org.nhsrc.web.contract;

public class FacilityRequest {
    private String uuid;
    private Boolean inactive;
    private String name;
    private String facilityTypeUUID;
    private String districtUUID;

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

    public void setDistrictUUID(String districtUUID) {
        this.districtUUID = districtUUID;
    }
}
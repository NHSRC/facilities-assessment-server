package org.nhsrc.dto.nin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisteredFacilityDTO {
    @JsonProperty("phc_chc_type")
    private String facilityType;

    @JsonProperty("nin_to_hfi")
    private String ninId;

    @JsonProperty("hfi_name")
    private String facilityName;

    @JsonProperty("state_name")
    private String state;

    @JsonProperty("district_name")
    private String district;

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getNinId() {
        return ninId;
    }

    public void setNinId(String ninId) {
        this.ninId = ninId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
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
        return facilityType.trim();
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getNinId() {
        return ninId.trim();
    }

    public void setNinId(String ninId) {
        this.ninId = ninId;
    }

    public String getFacilityName() {
        return facilityName.trim();
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getState() {
        return state.trim();
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district.trim();
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStateName() {
        return this.getState().replace("&", "and");
    }
}

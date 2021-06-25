package org.nhsrc.web.contract.assessment;

import java.util.List;

public class FacilityWithAssessmentsResponse {
    private String facilityName;
    private String districtName;
    private String stateName;
    private String facilityType;
    private String registryUniqueId;
    private List<FacilityAssessmentResponse> assessments;

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }


    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String distictName) {
        this.districtName = distictName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getRegistryUniqueId() {
        return registryUniqueId;
    }

    public void setRegistryUniqueId(String registryUniqueId) {
        this.registryUniqueId = registryUniqueId;
    }

    public List<FacilityAssessmentResponse> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<FacilityAssessmentResponse> assessments) {
        this.assessments = assessments;
    }
}

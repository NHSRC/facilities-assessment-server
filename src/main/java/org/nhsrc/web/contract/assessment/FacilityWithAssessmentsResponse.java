package org.nhsrc.web.contract.assessment;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.nhsrc.domain.assessment.*;

import java.util.List;


public class FacilityWithAssessmentsResponse {
    private String facilityName;
    private String districtName;
    private String stateName;
    private String facilityType;
    private String ninId;
    private List assessments;

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

    public String getNinId() {
        return ninId;
    }

    public void setNinId(String ninId) {
        this.ninId = ninId;
    }

    public List getAssessments() {
        return assessments;
    }

    public void setAssessments(List assessments) {
        this.assessments = assessments;
    }


}
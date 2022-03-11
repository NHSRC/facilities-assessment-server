package org.nhsrc.web.contract.assessment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.nhsrc.web.contract.UserResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.nhsrc.utils.DateUtils.DATE_FORMAT_STRING;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacilityAssessmentResponse {
    private String uuid;
    private String facilityName;
    private String assessmentToolName;
    private String assessmentTypeName;
    private String assessmentNumber;

    @JsonFormat(pattern = DATE_FORMAT_STRING)
    private Date assessmentStartDate;

    @JsonFormat(pattern = DATE_FORMAT_STRING)
    private Date assessmentEndDate;

    private List<AssessmentCustomInfoResponse> customInfos = new ArrayList<>();
    private List<String> departmentsAssessed = new ArrayList<>();
    private List<UserResponse> assessors = new ArrayList<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<AssessmentCustomInfoResponse> getCustomInfos() {
        return customInfos;
    }

    public void setCustomInfos(List<AssessmentCustomInfoResponse> customInfos) {
        this.customInfos = customInfos;
    }

    public List<String> getDepartmentsAssessed() {
        return departmentsAssessed;
    }

    public void setDepartmentsAssessed(List<String> departmentsAssessed) {
        this.departmentsAssessed = departmentsAssessed;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getAssessmentToolName() {
        return assessmentToolName;
    }

    public void setAssessmentToolName(String assessmentToolName) {
        this.assessmentToolName = assessmentToolName;
    }

    public String getAssessmentTypeName() {
        return assessmentTypeName;
    }

    public void setAssessmentTypeName(String assessmentTypeName) {
        this.assessmentTypeName = assessmentTypeName;
    }

    public String getAssessmentNumber() {
        return assessmentNumber;
    }

    public void setAssessmentNumber(String assessmentNumber) {
        this.assessmentNumber = assessmentNumber;
    }

    public Date getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(Date assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public Date getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(Date assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public List<UserResponse> getAssessors() {
        return assessors;
    }

    public void setAssessors(List<UserResponse> assessors) {
        this.assessors = assessors;
    }
}

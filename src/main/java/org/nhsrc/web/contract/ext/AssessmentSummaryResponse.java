package org.nhsrc.web.contract.ext;

import java.util.Date;

public class AssessmentSummaryResponse {
    private String systemId;

    private String program;
    private String assessmentTool;
    private String assessmentType;
    private String assessmentSeries;
    private Date assessmentStartDate;
    private Date assessmentEndDate;
    private String assessmentNumber;

    private String state;
    private String district;
    private String facility;
    private String facilityType;
    private String facilityNIN;

    private boolean inactive;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getAssessmentTool() {
        return assessmentTool;
    }

    public void setAssessmentTool(String assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentSeries() {
        return assessmentSeries;
    }

    public void setAssessmentSeries(String assessmentSeries) {
        this.assessmentSeries = assessmentSeries;
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

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getFacilityNIN() {
        return facilityNIN;
    }

    public void setFacilityNIN(String facilityNIN) {
        this.facilityNIN = facilityNIN;
    }

    public String getAssessmentNumber() {
        return assessmentNumber;
    }

    public void setAssessmentNumber(String assessmentNumber) {
        this.assessmentNumber = assessmentNumber;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }
}

package org.nhsrc.web.contract.assessment;

public class AssessmentCustomInfoResponse {
    private String assessmentMetaDataName;
    private String valueString;

    public AssessmentCustomInfoResponse(String assessmentMetaDataName, String valueString) {
        this.assessmentMetaDataName = assessmentMetaDataName;
        this.valueString = valueString;
    }

    public AssessmentCustomInfoResponse() {
    }

    public String getAssessmentMetaDataName() {
        return assessmentMetaDataName;
    }

    public void setAssessmentMetaDataName(String assessmentMetaDataName) {
        this.assessmentMetaDataName = assessmentMetaDataName;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
}
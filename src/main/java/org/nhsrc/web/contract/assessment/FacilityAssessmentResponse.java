package org.nhsrc.web.contract.assessment;

import java.util.ArrayList;
import java.util.List;

public class FacilityAssessmentResponse {
    private String assessmentUuid;
    private List<AssessmentCustomInfoResponse> customInfos = new ArrayList<>();
    private List<String> departmentsAssessed = new ArrayList<>();

    public String getAssessmentUuid() {
        return assessmentUuid;
    }

    public void setAssessmentUuid(String assessmentUuid) {
        this.assessmentUuid = assessmentUuid;
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
}
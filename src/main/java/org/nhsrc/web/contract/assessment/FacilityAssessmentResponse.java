package org.nhsrc.web.contract.assessment;

import java.util.ArrayList;
import java.util.List;

public class FacilityAssessmentResponse {
    private String uuid;
    private List<AssessmentCustomInfoResponse> customInfos = new ArrayList<>();
    private List<String> departmentsAssessed = new ArrayList<>();

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
}
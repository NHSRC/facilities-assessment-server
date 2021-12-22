package org.nhsrc.web.contract.assessment;

import org.nhsrc.web.contract.BaseRequest;

import java.util.ArrayList;
import java.util.List;

public class AssessmentNumberAssignmentRequest extends BaseRequest {
    private int id;
    private String assessmentNumber;
    private List<Integer> userIds = new ArrayList<>();
    private int assessmentTypeId;
    private int facilityId;

    public String getAssessmentNumber() {
        return assessmentNumber;
    }

    public void setAssessmentNumber(String assessmentNumber) {
        this.assessmentNumber = assessmentNumber;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public int getAssessmentTypeId() {
        return assessmentTypeId;
    }

    public void setAssessmentTypeId(int assessmentTypeId) {
        this.assessmentTypeId = assessmentTypeId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

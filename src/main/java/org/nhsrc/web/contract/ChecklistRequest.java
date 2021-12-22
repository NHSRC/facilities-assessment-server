package org.nhsrc.web.contract;

import java.util.ArrayList;
import java.util.List;

public class ChecklistRequest extends BaseRequest {
    private String name;
    private List<Integer> assessmentToolIds = new ArrayList<>();
    private String assessmentToolUUID;
    private int departmentId;
    private String departmentUUID;
    private List<Integer> areaOfConcernIds = new ArrayList<>();
    private Integer stateId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssessmentToolUUID() {
        return assessmentToolUUID;
    }

    public void setAssessmentToolUUID(String assessmentToolUUID) {
        this.assessmentToolUUID = assessmentToolUUID;
    }

    public String getDepartmentUUID() {
        return departmentUUID;
    }

    public void setDepartmentUUID(String departmentUUID) {
        this.departmentUUID = departmentUUID;
    }

    public List<Integer> getAssessmentToolIds() {
        return assessmentToolIds;
    }

    public void setAssessmentToolIds(List<Integer> assessmentToolIds) {
        this.assessmentToolIds = assessmentToolIds;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public List<Integer> getAreaOfConcernIds() {
        return areaOfConcernIds == null ? new ArrayList<>() : areaOfConcernIds;
    }

    public void setAreaOfConcernIds(List<Integer> areaOfConcernIds) {
        this.areaOfConcernIds = areaOfConcernIds;
    }
}

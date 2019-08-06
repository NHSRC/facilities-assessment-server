package org.nhsrc.web.contract;

import java.util.ArrayList;
import java.util.List;

public class ChecklistRequest {
    private String uuid;
    private Boolean inactive;
    private String name;
    private List<Integer> assessmentToolIds;
    private String assessmentToolUUID;
    private int departmentId;
    private String departmentUUID;
    private List<Integer> areaOfConcernIds;
    private Integer stateId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

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
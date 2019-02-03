package org.nhsrc.web.contract;

public class ChecklistRequest {
    private String uuid;
    private Boolean inactive;
    private String name;
    private int assessmentToolId;
    private String assessmentToolUUID;
    private int departmentId;
    private String departmentUUID;
    private String[] areasOfConcernUUIDs;
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

    public String[] getAreasOfConcernUUIDs() {
        return areasOfConcernUUIDs == null ? new String[0] : areasOfConcernUUIDs;
    }

    public void setAreasOfConcernUUIDs(String[] areasOfConcernUUIDs) {
        this.areasOfConcernUUIDs = areasOfConcernUUIDs;
    }

    public int getAssessmentToolId() {
        return assessmentToolId;
    }

    public void setAssessmentToolId(int assessmentToolId) {
        this.assessmentToolId = assessmentToolId;
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
}
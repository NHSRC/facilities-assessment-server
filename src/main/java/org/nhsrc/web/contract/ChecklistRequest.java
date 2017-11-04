package org.nhsrc.web.contract;

public class ChecklistRequest {
    private String uuid;
    private Boolean inactive;
    private String name;
    private String assessmentToolUUID;
    private String departmentUUID;
    private String[] areasOfConcernUUIDs;

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
        return areasOfConcernUUIDs;
    }

    public void setAreasOfConcernUUIDs(String[] areasOfConcernUUIDs) {
        this.areasOfConcernUUIDs = areasOfConcernUUIDs;
    }
}
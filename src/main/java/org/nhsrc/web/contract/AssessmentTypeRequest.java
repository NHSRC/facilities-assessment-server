package org.nhsrc.web.contract;

public class AssessmentTypeRequest {
    private String uuid;
    private int id;
    private Boolean inactive;
    private String name;
    private String shortName;
    private int assessmentToolModeId;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getAssessmentToolModeId() {
        return assessmentToolModeId;
    }

    public void setAssessmentToolModeId(int assessmentToolModeId) {
        this.assessmentToolModeId = assessmentToolModeId;
    }
}
package org.nhsrc.web.contract;

import java.util.List;

public class AssessmentToolRequest {
    private String uuid;
    private int id;
    private Boolean inactive;
    private String name;
    private int assessmentToolModeId;
    private String assessmentType;
    private List<Integer> checklistIds;

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

    public int getAssessmentToolModeId() {
        return assessmentToolModeId;
    }

    public void setAssessmentToolModeId(int assessmentToolModeId) {
        this.assessmentToolModeId = assessmentToolModeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public List<Integer> getChecklistIds() {
        return checklistIds;
    }

    public void setChecklistIds(List<Integer> checklistIds) {
        this.checklistIds = checklistIds;
    }
}
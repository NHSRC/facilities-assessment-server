package org.nhsrc.web.contract;

public class PrivilegeRequest {
    private int id;
    private String name;
    private Integer stateId;
    private Integer assessmentToolModeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getAssessmentToolModeId() {
        return assessmentToolModeId;
    }

    public void setAssessmentToolModeId(Integer assessmentToolModeId) {
        this.assessmentToolModeId = assessmentToolModeId;
    }
}
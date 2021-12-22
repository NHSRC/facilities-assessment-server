package org.nhsrc.web.contract;

public class AssessmentTypeRequest extends BaseRequest {
    private int id;
    private String name;
    private String shortName;
    private int assessmentToolModeId;

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

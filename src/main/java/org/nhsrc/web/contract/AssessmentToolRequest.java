package org.nhsrc.web.contract;

import java.util.ArrayList;
import java.util.List;

public class AssessmentToolRequest extends BaseRequest {
    private int id;
    private String name;
    private int assessmentToolModeId;
    private String assessmentType;
    private List<Integer> checklistIds = new ArrayList<>();
    private List<Integer> excludedStateIds = new ArrayList<>();
    private Integer stateId;
    private Integer sortOrder;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
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

    public List<Integer> getExcludedStateIds() {
        return excludedStateIds;
    }

    public void setExcludedStateIds(List<Integer> excludedStateIds) {
        this.excludedStateIds = excludedStateIds;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}

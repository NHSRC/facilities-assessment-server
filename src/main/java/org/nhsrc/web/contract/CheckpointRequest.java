package org.nhsrc.web.contract;

import java.util.ArrayList;
import java.util.List;

public class CheckpointRequest {
    private Integer id;
    private String uuid;
    private Boolean inactive;
    private String name;
    private String meansOfVerification;
    private String measurableElementUUID;
    private int measurableElementId;
    private String checklistUUID;
    private int checklistId;
    private Boolean assessmentMethodObservation;
    private Boolean assessmentMethodStaffInterview;
    private Boolean assessmentMethodPatientInterview;
    private Boolean assessmentMethodRecordReview;
    private Integer sortOrder;
    //legacy
    private Boolean isDefault;
    private Integer stateId;
    private boolean isOptional;
    private List<Integer> excludedStateIds = new ArrayList<>();

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

    public String getMeansOfVerification() {
        return meansOfVerification;
    }

    public void setMeansOfVerification(String meansOfVerification) {
        this.meansOfVerification = meansOfVerification;
    }

    public String getMeasurableElementUUID() {
        return measurableElementUUID;
    }

    public void setMeasurableElementUUID(String measurableElementUUID) {
        this.measurableElementUUID = measurableElementUUID;
    }

    public Boolean getAssessmentMethodObservation() {
        return assessmentMethodObservation;
    }

    public void setAssessmentMethodObservation(Boolean assessmentMethodObservation) {
        this.assessmentMethodObservation = assessmentMethodObservation;
    }

    public Boolean getAssessmentMethodStaffInterview() {
        return assessmentMethodStaffInterview;
    }

    public void setAssessmentMethodStaffInterview(Boolean assessmentMethodStaffInterview) {
        this.assessmentMethodStaffInterview = assessmentMethodStaffInterview;
    }

    public Boolean getAssessmentMethodPatientInterview() {
        return assessmentMethodPatientInterview;
    }

    public void setAssessmentMethodPatientInterview(Boolean assessmentMethodPatientInterview) {
        this.assessmentMethodPatientInterview = assessmentMethodPatientInterview;
    }

    public Boolean getAssessmentMethodRecordReview() {
        return assessmentMethodRecordReview;
    }

    public void setAssessmentMethodRecordReview(Boolean assessmentMethodRecordReview) {
        this.assessmentMethodRecordReview = assessmentMethodRecordReview;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getChecklistUUID() {
        return checklistUUID;
    }

    public void setChecklistUUID(String checklistUUID) {
        this.checklistUUID = checklistUUID;
    }

    public int getMeasurableElementId() {
        return measurableElementId;
    }

    public void setMeasurableElementId(int measurableElementId) {
        this.measurableElementId = measurableElementId;
    }

    public int getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }

    public List<Integer> getExcludedStateIds() {
        return excludedStateIds;
    }

    public void setExcludedStateIds(List<Integer> excludedStateIds) {
        this.excludedStateIds = excludedStateIds;
    }
}
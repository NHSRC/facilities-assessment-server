package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "checkpoint")
@BatchSize(size = 25)
public class Checkpoint extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "means_of_verification", unique = true, nullable = false, length = 1023)
    private String meansOfVerification;

    @ManyToOne(targetEntity = MeasurableElement.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "measurable_element_id")
    @NotNull
    private MeasurableElement measurableElement;

    @ManyToOne(targetEntity = Checklist.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    @NotNull
    private Checklist checklist;

    @ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "am_observation")
    @NotNull
    private Boolean assessmentMethodObservation = false;

    @Column(name = "am_staff_interview")
    @NotNull
    private Boolean assessmentMethodStaffInterview = false;

    @Column(name = "am_patient_interview")
    @NotNull
    private Boolean assessmentMethodPatientInterview = false;

    @Column(name = "am_record_review")
    @NotNull
    private Boolean assessmentMethodRecordReview = false;

    @Column(name = "sort_order")
    @NotNull
    private Integer sortOrder = 0;

    @Column(name = "is_optional")
    @NotNull
    private boolean isOptional = false;

    @Column(name = "score_levels")
    @NotNull
    private Integer scoreLevels = 3;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public MeasurableElement getMeasurableElement() {
        return measurableElement;
    }

    @JsonIgnore
    public Checklist getChecklist() {
        return checklist;
    }

    @JsonProperty("checklistId")
    public Integer _getChecklistId() {
        return this.checklist.getId();
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public void setMeasurableElement(MeasurableElement measurableElement) {
        this.measurableElement = measurableElement;
    }

    public String getMeansOfVerification() {
        return meansOfVerification;
    }

    public void setMeansOfVerification(String meansOfVerification) {
        this.meansOfVerification = meansOfVerification;
    }

    public Boolean getAssessmentMethodObservation() {
        return assessmentMethodObservation;
    }

    public void setAssessmentMethodObservation(Boolean assessmentMethodObservation) {
        this.assessmentMethodObservation = assessmentMethodObservation == null ? false : assessmentMethodObservation;
    }

    public Boolean getAssessmentMethodStaffInterview() {
        return assessmentMethodStaffInterview;
    }

    public void setAssessmentMethodStaffInterview(Boolean assessmentMethodStaffInterview) {
        this.assessmentMethodStaffInterview = assessmentMethodStaffInterview == null ? false : assessmentMethodStaffInterview;
    }

    public Boolean getAssessmentMethodPatientInterview() {
        return assessmentMethodPatientInterview;
    }

    public void setAssessmentMethodPatientInterview(Boolean assessmentMethodPatientInterview) {
        this.assessmentMethodPatientInterview = assessmentMethodPatientInterview == null ? false : assessmentMethodPatientInterview;
    }

    public Boolean getAssessmentMethodRecordReview() {
        return assessmentMethodRecordReview;
    }

    public void setAssessmentMethodRecordReview(Boolean assessmentMethodRecordReview) {
        this.assessmentMethodRecordReview = assessmentMethodRecordReview == null ? false : assessmentMethodRecordReview;
    }

    @JsonIgnore
    public State getState() {
        return state;
    }

    @JsonProperty("stateId")
    public Integer _getStateId() {
        return state == null ? null : state.getId();
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }

    public Integer getScoreLevels() {
        return scoreLevels;
    }

    public void setScoreLevels(Integer scoreLevels) {
        this.scoreLevels = scoreLevels;
    }

    @JsonProperty("measurableElementId")
    public Integer _getMeasurableElementId() {
        return this.measurableElement.getId();
    }

    @JsonProperty("standardId")
    public Integer _getStandardId() {
        return this.measurableElement._getStandardId();
    }

    @JsonProperty("areaOfConcernId")
    public Integer _getAreaOfConcernId() {
        return this.measurableElement.getStandard()._getAreaOfConcernId();
    }

    @JsonProperty("assessmentToolId")
    public List<Integer> _getAssessmentToolId() {
        return this.measurableElement.getStandard().getAreaOfConcern()._getAssessmentToolIds();
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "name='" + name + '\'' +
                ", " + checklist + '\'' +
                ", " + measurableElement +
                '}';
    }
}

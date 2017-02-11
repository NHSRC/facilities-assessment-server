package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "checkpoint")
public class Checkpoint extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "means_of_verification", unique = true, nullable = false, length = 1023)
    private String meansOfVerification;

    @Column(name = "reference", unique = true, nullable = false)
    private String reference;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = MeasurableElement.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "measurable_element_id")
    @NotNull
    private MeasurableElement measurableElement;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = Checklist.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "checklist_id")
    @NotNull
    private Checklist checklist;

    @Column(name = "is_default")
    @NotNull
    private Boolean isDefault = true;

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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public MeasurableElement getMeasurableElement() {
        return measurableElement;
    }

    public Checklist getChecklist() {
        return checklist;
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

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
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
}

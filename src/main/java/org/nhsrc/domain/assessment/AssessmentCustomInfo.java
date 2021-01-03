package org.nhsrc.domain.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.nhsrc.domain.AbstractPersistable;
import org.nhsrc.domain.metadata.AssessmentMetaData;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "assessment_custom_info")
public class AssessmentCustomInfo extends AbstractPersistable {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_metadata_id")
    @NotNull
    private AssessmentMetaData assessmentMetaData;

    @Column(name = "value_string")
    private String valueString;

    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @JsonIgnore
    public AssessmentMetaData getAssessmentMetaData() {
        return assessmentMetaData;
    }

    public void setAssessmentMetaData(AssessmentMetaData assessmentMetaData) {
        this.assessmentMetaData = assessmentMetaData;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    @JsonIgnore
    public FacilityAssessment getFacilityAssessment() {
        return facilityAssessment;
    }

    public void setFacilityAssessment(FacilityAssessment facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }

    public String getAssessmentMetaDataUuid() {
        return getAssessmentMetaData().getUuidString();
    }

    public String getFacilityAssessmentUuid() {
        return getFacilityAssessment().getUuidString();
    }
}
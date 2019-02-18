package org.nhsrc.domain.missing;

import org.nhsrc.domain.FacilityAssessment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "facility_assessment_missing_checkpoint")
public class FacilityAssessmentMissingCheckpoint extends BaseMissingEntity {
    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @ManyToOne(targetEntity = MissingCheckpoint.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "missing_checkpoint_id")
    @NotNull
    private MissingCheckpoint missingCheckpoint;

    public void setFacilityAssessment(FacilityAssessment facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }

    public void setMissingCheckpoint(MissingCheckpoint missingCheckpoint) {
        this.missingCheckpoint = missingCheckpoint;
    }

    public String getMissingCheckpointName() {
        return missingCheckpoint.getName();
    }

    public int getChecklistId() {
        return missingCheckpoint.getChecklist().getId();
    }

    public int getFacilityAssessmentId() {
        return this.facilityAssessment.getId();
    }

    public String getMeasurableElementReference() {
        return missingCheckpoint.getMeasurableElementReference();
    }
}
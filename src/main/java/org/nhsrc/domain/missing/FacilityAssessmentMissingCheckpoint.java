package org.nhsrc.domain.missing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.nhsrc.domain.FacilityAssessment;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "facility_assessment_missing_checkpoint")
public class FacilityAssessmentMissingCheckpoint implements Persistable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @ManyToOne(targetEntity = MissingCheckpoint.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "missing_checkpoint_id")
    @NotNull
    private MissingCheckpoint missingCheckpoint;

    @JsonIgnore
    public FacilityAssessment getFacilityAssessment() {
        return facilityAssessment;
    }

    public void setFacilityAssessment(FacilityAssessment facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null || id == 0;
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
}
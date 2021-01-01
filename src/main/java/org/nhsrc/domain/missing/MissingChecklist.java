package org.nhsrc.domain.missing;

import org.nhsrc.domain.assessment.FacilityAssessment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "missing_checklist")
public class MissingChecklist extends BaseMissingEntity {
    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @Column(name = "name", nullable = false)
    private String name;

    public void setFacilityAssessment(FacilityAssessment facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
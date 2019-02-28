package org.nhsrc.domain.scores;

import org.nhsrc.domain.BaseEntity;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.Standard;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "standard_score")
public class StandardScore extends BaseEntity {
    @ManyToOne(targetEntity = Standard.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
    @NotNull
    private Standard standard;

    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public FacilityAssessment getFacilityAssessment() {
        return facilityAssessment;
    }

    public void setFacilityAssessment(FacilityAssessment facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }
}
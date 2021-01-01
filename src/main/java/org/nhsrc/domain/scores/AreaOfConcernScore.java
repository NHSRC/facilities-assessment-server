package org.nhsrc.domain.scores;

import org.nhsrc.domain.BaseEntity;
import org.nhsrc.domain.AreaOfConcern;
import org.nhsrc.domain.assessment.FacilityAssessment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "area_of_concern_score")
public class AreaOfConcernScore extends BaseEntity {
    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @ManyToOne(targetEntity = AreaOfConcern.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "area_of_concern_id")
    @NotNull
    private AreaOfConcern areaOfConcern;

    public FacilityAssessment getFacilityAssessment() {
        return facilityAssessment;
    }

    public void setFacilityAssessment(FacilityAssessment facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }

    public AreaOfConcern getAreaOfConcern() {
        return areaOfConcern;
    }

    public void setAreaOfConcern(AreaOfConcern areaOfConcern) {
        this.areaOfConcern = areaOfConcern;
    }
}
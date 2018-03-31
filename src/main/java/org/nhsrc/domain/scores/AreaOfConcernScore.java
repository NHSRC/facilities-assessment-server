package org.nhsrc.domain.scores;

import org.nhsrc.domain.AbstractTransactionalEntity;
import org.nhsrc.domain.AreaOfConcern;
import org.nhsrc.domain.FacilityAssessment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "area_of_concern_score")
public class AreaOfConcernScore extends AbstractTransactionalEntity {
    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @ManyToOne(targetEntity = AreaOfConcern.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "area_of_concern_id")
    @NotNull
    private AreaOfConcern areaOfConcern;

    @Column(name = "numerator")
    private int numerator;

    @Column(name = "denominator")
    private int denominator;

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

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }
}
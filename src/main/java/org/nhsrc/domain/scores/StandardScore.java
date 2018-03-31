package org.nhsrc.domain.scores;

import org.nhsrc.domain.AbstractTransactionalEntity;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.Standard;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "standard_score")
public class StandardScore extends AbstractTransactionalEntity {
    @ManyToOne(targetEntity = Standard.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
    @NotNull
    private Standard standard;

    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @Column(name = "numerator")
    private int numerator;

    @Column(name = "denominator")
    private int denominator;

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
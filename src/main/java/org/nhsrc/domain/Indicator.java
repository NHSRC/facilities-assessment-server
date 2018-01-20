package org.nhsrc.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "indicator")
public class Indicator extends AbstractEntity {
    @Column(name = "numerator_value")
    private Integer numeratorValue;

    @Column(name = "denominator_value")
    private Integer denominatorValue;

    @Column(name = "indicator_value")
    private Integer indicatorValue;

    @ManyToOne(targetEntity = IndicatorDefinition.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "indicator_definition_id")
    @NotNull
    private IndicatorDefinition indicatorDefinition;

    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    public Integer getNumeratorValue() {
        return numeratorValue;
    }

    public void setNumeratorValue(Integer numeratorValue) {
        this.numeratorValue = numeratorValue;
    }

    public Integer getDenominatorValue() {
        return denominatorValue;
    }

    public void setDenominatorValue(Integer denominatorValue) {
        this.denominatorValue = denominatorValue;
    }

    public Integer getIndicatorValue() {
        return indicatorValue;
    }

    public void setIndicatorValue(Integer indicatorValue) {
        this.indicatorValue = indicatorValue;
    }

    public IndicatorDefinition getIndicatorDefinition() {
        return indicatorDefinition;
    }

    public void setIndicatorDefinition(IndicatorDefinition indicatorDefinition) {
        this.indicatorDefinition = indicatorDefinition;
    }

    public FacilityAssessment getFacilityAssessment() {
        return facilityAssessment;
    }

    public void setFacilityAssessment(FacilityAssessment facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }
}
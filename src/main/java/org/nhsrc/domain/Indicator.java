package org.nhsrc.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "indicator")
public class Indicator extends AbstractEntity {
    @Column(name = "numeric_value")
    private Integer numericValue;

    @Column(name = "date_value")
    private Date dateValue;

    @Column(name = "coded_value")
    private String codedValue;

    @ManyToOne(targetEntity = IndicatorDefinition.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "indicator_definition_id")
    @NotNull
    private IndicatorDefinition indicatorDefinition;

    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    public Integer getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(Integer numericValue) {
        this.numericValue = numericValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
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

    public String getCodedValue() {
        return codedValue;
    }

    public void setCodedValue(String codedValue) {
        this.codedValue = codedValue;
    }
}
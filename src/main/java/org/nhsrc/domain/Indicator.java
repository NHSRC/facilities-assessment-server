package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "indicator")
public class Indicator extends BaseEntity {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Column(name = "numeric_value")
    private Integer numericValue;

    @Column(name = "date_value")
    private Date dateValue;

    @Column(name = "coded_value")
    private String codedValue;

    @ManyToOne(targetEntity = IndicatorDefinition.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "indicator_definition_id")
    @NotNull
    @RestResource(exported = false)
    private IndicatorDefinition indicatorDefinition;

    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    @RestResource(exported = false)
    private FacilityAssessment facilityAssessment;

    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    private UUID uuid;

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

    @JsonIgnore
    public IndicatorDefinition getIndicatorDefinition() {
        return indicatorDefinition;
    }

    public void setIndicatorDefinition(IndicatorDefinition indicatorDefinition) {
        this.indicatorDefinition = indicatorDefinition;
    }

    @JsonIgnore
    public FacilityAssessment getFacilityAssessment() {
        return facilityAssessment;
    }

    @JsonProperty("facilityAssessmentId")
    public Integer _getFacilityAssessmentId() {
        return this.facilityAssessment.getId();
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getValue() {
        IndicatorDataType dataType = this.getIndicatorDefinition().getDataType();
        switch (dataType) {
            case Date:
            case Month:
                return dateFormatter.format(dateValue);
            case Coded:
                return codedValue;
            case Numeric:
            case Percentage:
                return numericValue.toString();
        }
        throw new RuntimeException(String.format("Indicator with data type:%s that is not support", dataType));
    }
}

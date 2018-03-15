package org.nhsrc.dto;

import java.util.Date;
import java.util.UUID;

public class IndicatorDTO {
    private UUID uuid;
    private UUID indicatorDefinition;
    private Integer numericValue;
    private java.util.Date dateValue;
    private String codedValue;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getIndicatorDefinition() {
        return indicatorDefinition;
    }

    public void setIndicatorDefinition(UUID indicatorDefinition) {
        this.indicatorDefinition = indicatorDefinition;
    }

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

    public String getCodedValue() {
        return codedValue;
    }

    public void setCodedValue(String codedValue) {
        this.codedValue = codedValue;
    }
}

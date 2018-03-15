package org.nhsrc.dto;

import java.util.List;
import java.util.UUID;

public class IndicatorListDTO {
    private UUID facilityAssessment;
    private List<IndicatorDTO> indicators;

    public UUID getFacilityAssessment() {
        return facilityAssessment;
    }

    public void setFacilityAssessment(UUID facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }

    public List<IndicatorDTO> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<IndicatorDTO> indicators) {
        this.indicators = indicators;
    }
}

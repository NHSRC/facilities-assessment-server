package org.nhsrc.builder;

import org.nhsrc.dto.FacilityAssessmentAppDTO;

import java.util.Date;
import java.util.UUID;

public class FacilityAssessmentDTOBuilder {

    private FacilityAssessmentAppDTO facilityAssessment;

    public FacilityAssessmentDTOBuilder() {
        facilityAssessment = new FacilityAssessmentAppDTO();
        facilityAssessment.setUuid(UUID.randomUUID());
        facilityAssessment.setFacility(UUID.fromString("b4d3a2ec-f17f-475c-bca1-b92d13bc469e"));
        facilityAssessment.setAssessmentTool(UUID.fromString("77cceb53-7d71-456c-a9ee-c870774707ad"));
        facilityAssessment.setStartDate(new Date());
    }

    public FacilityAssessmentDTOBuilder withFacility(String facilityUUID) {
        facilityAssessment.setFacility(UUID.fromString(facilityUUID));
        return this;
    }

    public FacilityAssessmentDTOBuilder withUUID(String uuid) {
        facilityAssessment.setUuid(UUID.fromString(uuid));
        return this;
    }

    public FacilityAssessmentDTOBuilder withAssessmentTool(String assessmentToolUUID) {
        facilityAssessment.setAssessmentTool(UUID.fromString(assessmentToolUUID));
        return this;
    }

    public FacilityAssessmentDTOBuilder asDistrictHospital() {
        return this.withAssessmentTool("c76a1683-ea08-4472-a7b2-6588716501e5");
    }

    public FacilityAssessmentDTOBuilder asPHC() {
        return this.withAssessmentTool("d8de2716-cc30-4af7-9011-638e34077222");
    }

    public FacilityAssessmentDTOBuilder withStartDate(Date startDate) {
        facilityAssessment.setStartDate(startDate);
        return this;
    }

    public FacilityAssessmentAppDTO build() {
        return this.facilityAssessment;
    }
}

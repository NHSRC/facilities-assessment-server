package org.nhsrc.mapper;

import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.dto.assessment.BaseFacilityAssessmentDTO;

public class FacilityAssessmentMapper {
    public static FacilityAssessment fromDTO(BaseFacilityAssessmentDTO facilityAssessmentDTO) {
        FacilityAssessment facilityAssessment = new FacilityAssessment();
        facilityAssessment.setUuid(facilityAssessmentDTO.getUuid());
        facilityAssessment.setEndDate(facilityAssessmentDTO.getEndDate());
        return facilityAssessment;
    }
}

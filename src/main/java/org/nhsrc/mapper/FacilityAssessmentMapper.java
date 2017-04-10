package org.nhsrc.mapper;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.dto.FacilityAssessmentDTO;

public class FacilityAssessmentMapper {
    public static FacilityAssessment fromDTO(FacilityAssessmentDTO facilityAssessmentDTO,
                                             Facility facility,
                                             AssessmentTool assessmentTool) {
        FacilityAssessment facilityAssessment = new FacilityAssessment();
        facilityAssessment.setFacility(facility);
        facilityAssessment.setAssessmentTool(assessmentTool);
        facilityAssessment.setUuid(facilityAssessmentDTO.getUuid());
        facilityAssessment.setStartDate(facilityAssessmentDTO.getStartDate());
        facilityAssessment.setEndDate(facilityAssessmentDTO.getEndDate());
        return facilityAssessment;
    }
}

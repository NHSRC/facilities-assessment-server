package org.nhsrc.mapper;

import org.nhsrc.domain.*;
import org.nhsrc.dto.BaseFacilityAssessmentDTO;

public class FacilityAssessmentMapper {
    public static FacilityAssessment fromDTO(BaseFacilityAssessmentDTO facilityAssessmentDTO,
                                             Facility facility,
                                             AssessmentTool assessmentTool, AssessmentType assessmentType, State state, District district, FacilityType facilityType) {
        FacilityAssessment facilityAssessment = new FacilityAssessment();
        facilityAssessment.setFacility(facility);
        facilityAssessment.setFacilityName(facilityAssessmentDTO.getFacilityName());
        facilityAssessment.setState(state);
        facilityAssessment.setDistrict(district);
        facilityAssessment.setFacilityType(facilityType);
        facilityAssessment.setFacilityName(facilityAssessmentDTO.getFacilityName());

        facilityAssessment.setAssessmentTool(assessmentTool);
        facilityAssessment.setUuid(facilityAssessmentDTO.getUuid());
        facilityAssessment.setStartDate(facilityAssessmentDTO.getStartDate());
        facilityAssessment.setEndDate(facilityAssessmentDTO.getEndDate());
        facilityAssessment.setSeriesName(facilityAssessmentDTO.getSeriesName());
        facilityAssessment.setAssessmentType(assessmentType);
        return facilityAssessment;
    }
}

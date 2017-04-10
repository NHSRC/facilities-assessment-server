package org.nhsrc.service;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.mapper.FacilityAssessmentMapper;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FacilityAssessmentService {

    private final FacilityRepository facilityRepository;
    private final AssessmentToolRepository assessmentToolRepository;
    private final FacilityAssessmentRepository facilityAssessmentRepository;

    @Autowired
    public FacilityAssessmentService(FacilityRepository facilityRepository,
                                     AssessmentToolRepository assessmentToolRepository,
                                     FacilityAssessmentRepository facilityAssessmentRepository) {
        this.facilityRepository = facilityRepository;
        this.assessmentToolRepository = assessmentToolRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
    }

    public FacilityAssessment save(FacilityAssessmentDTO facilityAssessmentDTO) {
        Facility facility = facilityRepository.findByUuid(facilityAssessmentDTO.getFacility());
        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(facilityAssessmentDTO.getAssessmentTool());
        FacilityAssessment facilityAssessment = FacilityAssessmentMapper.fromDTO(facilityAssessmentDTO, facility, assessmentTool);
        return facilityAssessmentRepository.save(facilityAssessment);
    }
}

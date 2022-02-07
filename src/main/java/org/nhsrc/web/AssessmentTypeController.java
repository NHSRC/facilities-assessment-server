package org.nhsrc.web;

import org.nhsrc.domain.AssessmentToolMode;
import org.nhsrc.domain.AssessmentType;
import org.nhsrc.mapper.AssessmentToolComponentMapper;
import org.nhsrc.repository.AssessmentToolModeRepository;
import org.nhsrc.repository.AssessmentTypeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.AssessmentTypeRequest;
import org.nhsrc.web.contract.ext.AssessmentToolResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class AssessmentTypeController {
    private final AssessmentTypeRepository assessmentTypeRepository;
    private final AssessmentToolModeRepository assessmentToolModeRepository;

    @Autowired
    public AssessmentTypeController(AssessmentTypeRepository assessmentTypeRepository, AssessmentToolModeRepository assessmentToolModeRepository) {
        this.assessmentTypeRepository = assessmentTypeRepository;
        this.assessmentToolModeRepository = assessmentToolModeRepository;
    }

    @RequestMapping(value = "/assessmentTypes", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public AssessmentType save(@RequestBody AssessmentTypeRequest request) {
        AssessmentType assessmentType = Repository.findByUuidOrCreate(request.getUuid(), assessmentTypeRepository, new AssessmentType());
        assessmentType.setName(request.getName());
        assessmentType.setShortName(request.getShortName());
        assessmentType.setInactive(request.getInactive());
        AssessmentToolMode assessmentToolMode = Repository.findById(request.getAssessmentToolModeId(), assessmentToolModeRepository);
        assessmentType.setAssessmentToolMode(assessmentToolMode);
        return assessmentTypeRepository.save(assessmentType);
    }

    @RequestMapping(value = "/assessmentType/search/find", method = {RequestMethod.GET})
    public List<AssessmentType> find(@RequestParam(value = "assessment_tool_mode") Integer assessmentToolModeId) {
        return assessmentTypeRepository.findAllByAssessmentToolModeId(assessmentToolModeId);
    }

    @RequestMapping(value = "/assessmentTypes/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public AssessmentType delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, assessmentTypeRepository);
    }

    @RequestMapping(value = "/ext/assessmentType", method = {RequestMethod.GET})
    public Page<AssessmentToolResponse.AssessmentTypeResponse> getPrograms(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable) {
        Page<AssessmentType> assessmentTypes = assessmentTypeRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(lastModifiedDateTime, pageable);
        return assessmentTypes.map(AssessmentToolComponentMapper::mapAssessmentType);
    }
}

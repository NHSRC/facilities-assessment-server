package org.nhsrc.web;

import org.nhsrc.domain.IndicatorDataType;
import org.nhsrc.domain.IndicatorDefinition;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.repository.IndicatorDefinitionRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.IndicatorDefinitionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class IndicatorDefinitionController {
    private IndicatorDefinitionRepository indicatorDefinitionRepository;
    private AssessmentToolRepository assessmentToolRepository;

    @Autowired
    public IndicatorDefinitionController(IndicatorDefinitionRepository indicatorDefinitionRepository, AssessmentToolRepository assessmentToolRepository) {
        this.indicatorDefinitionRepository = indicatorDefinitionRepository;
        this.assessmentToolRepository = assessmentToolRepository;
    }

    @RequestMapping(value = "/indicatorDefinitions", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public IndicatorDefinition save(@RequestBody IndicatorDefinitionRequest request) {
        IndicatorDefinition indicatorDefinition = Repository.findByUuidOrCreate(request.getUuid(), indicatorDefinitionRepository, new IndicatorDefinition());
        indicatorDefinition.setName(request.getName());
        indicatorDefinition.setAssessmentTool(Repository.findById(request.getAssessmentToolId(), assessmentToolRepository));
        indicatorDefinition.setCodedValues(request.getCodedValues());
        indicatorDefinition.setDataType(IndicatorDataType.valueOf(request.getDataType()));
        indicatorDefinition.setFormula(request.getFormula());
        indicatorDefinition.setInactive(request.getInactive());
        indicatorDefinition.setOutput(request.getOutput());
        indicatorDefinition.setSortOrder(request.getSortOrder());
        indicatorDefinition.setSymbol(request.getSymbol());
        return indicatorDefinitionRepository.save(indicatorDefinition);
    }
    
    @RequestMapping(value = "/indicatorDefinitions/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public IndicatorDefinition delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, indicatorDefinitionRepository);
    }
}
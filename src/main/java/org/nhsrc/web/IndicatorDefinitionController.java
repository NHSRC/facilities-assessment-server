package org.nhsrc.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.IndicatorDataType;
import org.nhsrc.domain.IndicatorDefinition;
import org.nhsrc.mapper.AssessmentToolComponentMapper;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.repository.IndicatorDefinitionRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.utils.JsonUtil;
import org.nhsrc.web.contract.IndicatorDefinitionRequest;
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
public class IndicatorDefinitionController {
    private final IndicatorDefinitionRepository indicatorDefinitionRepository;
    private final AssessmentToolRepository assessmentToolRepository;

    @Autowired
    public IndicatorDefinitionController(IndicatorDefinitionRepository indicatorDefinitionRepository, AssessmentToolRepository assessmentToolRepository) {
        this.indicatorDefinitionRepository = indicatorDefinitionRepository;
        this.assessmentToolRepository = assessmentToolRepository;
    }

    @RequestMapping(value = "/indicatorDefinitions", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public IndicatorDefinition save(@RequestBody IndicatorDefinitionRequest request) throws JsonProcessingException {
        IndicatorDefinition indicatorDefinition = Repository.findByUuidOrCreate(request.getUuid(), indicatorDefinitionRepository, new IndicatorDefinition());
        indicatorDefinition.setName(request.getName());
        indicatorDefinition.setAssessmentTool(Repository.findById(request.getAssessmentToolId(), assessmentToolRepository));
        List<String> codedValues = request.getCodedValuesJson();
        if (codedValues != null && codedValues.size() > 0)
            indicatorDefinition.setCodedValues(JsonUtil.OBJECT_MAPPER.writeValueAsString(codedValues));
        indicatorDefinition.setDataType(IndicatorDataType.valueOf(request.getDataType()));
        indicatorDefinition.setFormula(request.getFormula());
        indicatorDefinition.setInactive(request.getInactive());
        indicatorDefinition.setOutput(request.isOutput());
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

    @RequestMapping(value = "/ext/indicatorDefinition", method = {RequestMethod.GET})
    public Page<AssessmentToolResponse.IndicatorResponse> getIndicatorDefinitions(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable) {
        Page<IndicatorDefinition> indicatorDefinitions = indicatorDefinitionRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(lastModifiedDateTime, pageable);
        return indicatorDefinitions.map(AssessmentToolComponentMapper::mapIndicatorDefinitions);
    }
}

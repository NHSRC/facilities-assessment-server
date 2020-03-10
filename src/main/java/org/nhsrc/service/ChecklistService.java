package org.nhsrc.service;

import org.nhsrc.domain.*;
import org.nhsrc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChecklistService {
    private AreaOfConcernRepository areaOfConcernRepository;
    private ChecklistRepository checklistRepository;
    private AssessmentToolRepository assessmentToolRepository;
    private ExcludedAssessmentToolStateRepository excludedAssessmentToolStateRepository;
    private StateRepository stateRepository;

    @Autowired
    public ChecklistService(AreaOfConcernRepository areaOfConcernRepository, ChecklistRepository checklistRepository, AssessmentToolRepository assessmentToolRepository, ExcludedAssessmentToolStateRepository excludedAssessmentToolStateRepository, StateRepository stateRepository) {
        this.areaOfConcernRepository = areaOfConcernRepository;
        this.checklistRepository = checklistRepository;
        this.assessmentToolRepository = assessmentToolRepository;
        this.excludedAssessmentToolStateRepository = excludedAssessmentToolStateRepository;
        this.stateRepository = stateRepository;
    }

    public void mergeAreaOfConcerns(Checklist checklist, Set<Integer> incidentAreaOfConcernIds) {
        HashSet<Integer> existingAreaOfConcernIds = new HashSet<>(checklist.getAreaOfConcernIds());
        existingAreaOfConcernIds.removeAll(incidentAreaOfConcernIds);
        existingAreaOfConcernIds.forEach(removedAreaOfConcern -> checklist.removeAreaOfConcern(areaOfConcernRepository.findOne(removedAreaOfConcern)));
        for (Integer areaOfConcernId : incidentAreaOfConcernIds) {
            checklist.addAreaOfConcern(areaOfConcernRepository.findOne(areaOfConcernId));
        }
    }

    public List<Integer> getChecklistsForState(String stateName) {
        State state = stateRepository.findByName(stateName);
        List<AssessmentTool> assessmentTools = this.getAssessmentToolsForState(state);
        List<Checklist> checklistsForAllStates = checklistRepository.findByAssessmentToolsIdIn(assessmentTools.stream().map(BaseEntity::getId).collect(Collectors.toList()));
        return checklistsForAllStates.stream().filter(checklist -> checklist.getState() == null || checklist.getState().equals(state)).map(BaseEntity::getId).collect(Collectors.toList());
    }

    public List<AssessmentTool> getAssessmentToolsForState(Integer stateId) {
        State state = stateRepository.findOne(stateId);
        return getAssessmentToolsForState(state);
    }

    public List<AssessmentTool> getAssessmentToolsForState(String stateName) {
        State state = stateRepository.findByName(stateName);
        return this.getAssessmentToolsForState(state);
    }

    public List<AssessmentTool> getAssessmentToolsForState(State state) {
        List<AssessmentTool> assessmentTools = assessmentToolRepository.findByStateOrStateIsNullOrderByAssessmentToolModeNameAscNameAsc(state);
        List<ExcludedAssessmentToolState> excluded = excludedAssessmentToolStateRepository.findByState(state);
        return assessmentTools.stream().filter(assessmentTool -> excluded.stream().filter(assessmentToolState -> assessmentToolState.getAssessmentTool().equals(assessmentTool)).findAny().orElse(null) == null).collect(Collectors.toList());
    }
}
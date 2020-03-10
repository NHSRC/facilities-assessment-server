package org.nhsrc.service;

import org.nhsrc.domain.*;
import org.nhsrc.repository.AreaOfConcernRepository;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.repository.ChecklistRepository;
import org.nhsrc.repository.ExcludedAssessmentToolStateRepository;
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

    @Autowired
    public ChecklistService(AreaOfConcernRepository areaOfConcernRepository, ChecklistRepository checklistRepository, AssessmentToolRepository assessmentToolRepository, ExcludedAssessmentToolStateRepository excludedAssessmentToolStateRepository) {
        this.areaOfConcernRepository = areaOfConcernRepository;
        this.checklistRepository = checklistRepository;
        this.assessmentToolRepository = assessmentToolRepository;
        this.excludedAssessmentToolStateRepository = excludedAssessmentToolStateRepository;
    }

    public void mergeAreaOfConcerns(Checklist checklist, Set<Integer> incidentAreaOfConcernIds) {
        HashSet<Integer> existingAreaOfConcernIds = new HashSet<>(checklist.getAreaOfConcernIds());
        existingAreaOfConcernIds.removeAll(incidentAreaOfConcernIds);
        existingAreaOfConcernIds.forEach(removedAreaOfConcern -> checklist.removeAreaOfConcern(areaOfConcernRepository.findOne(removedAreaOfConcern)));
        for (Integer areaOfConcernId : incidentAreaOfConcernIds) {
            checklist.addAreaOfConcern(areaOfConcernRepository.findOne(areaOfConcernId));
        }
    }

    public List<Integer> getChecklistsForState(State state) {
        List<AssessmentTool> assessmentTools = this.getAssessmentToolsForState(state.getId());
        List<Checklist> checklistsForAllStates = checklistRepository.findByAssessmentToolsIdIn(assessmentTools.stream().map(BaseEntity::getId).collect(Collectors.toList()));
        return checklistsForAllStates.stream().filter(checklist -> checklist.getState() == null || checklist.getState().equals(state)).map(BaseEntity::getId).collect(Collectors.toList());
    }

    public List<AssessmentTool> getAssessmentToolsForState(Integer stateId) {
        List<AssessmentTool> assessmentTools = assessmentToolRepository.findByStateIdOrStateIsNullOrderByAssessmentToolModeNameAscNameAsc(stateId);
        List<ExcludedAssessmentToolState> excluded = excludedAssessmentToolStateRepository.findByStateId(stateId);
        return assessmentTools.stream().filter(assessmentTool -> excluded.stream().filter(assessmentToolState -> assessmentToolState.getAssessmentTool().equals(assessmentTool)).findAny().orElse(null) == null).collect(Collectors.toList());
    }
}
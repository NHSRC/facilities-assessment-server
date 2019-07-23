package org.nhsrc.service;

import org.nhsrc.domain.Checklist;
import org.nhsrc.repository.AreaOfConcernRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChecklistService {
    private AreaOfConcernRepository areaOfConcernRepository;

    public ChecklistService(AreaOfConcernRepository areaOfConcernRepository) {
        this.areaOfConcernRepository = areaOfConcernRepository;
    }

    public void mergeAreaOfConcerns(Checklist checklist, Set<Integer> incidentAreaOfConcernIds) {
        HashSet<Integer> existingAreaOfConcernIds = new HashSet<>(checklist.getAreaOfConcernIds());
        existingAreaOfConcernIds.removeAll(incidentAreaOfConcernIds);
        existingAreaOfConcernIds.forEach(removedAreaOfConcern -> checklist.removeAreaOfConcern(areaOfConcernRepository.findOne(removedAreaOfConcern)));
        for (Integer areaOfConcernId : incidentAreaOfConcernIds) {
            checklist.addAreaOfConcern(areaOfConcernRepository.findOne(areaOfConcernId));
        }
    }
}
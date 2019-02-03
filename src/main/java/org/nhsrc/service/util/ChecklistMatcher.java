package org.nhsrc.service.util;

import org.nhsrc.domain.Checklist;

import java.util.List;

public class ChecklistMatcher {
    public static boolean isConflicting(List<Checklist> checklists, Integer stateId, Integer checklistId) {
        Checklist matchingChecklist = checklists.stream().filter(x -> (
                (x.getState() == null)
                        || (x.getState() != null && (x.getState().getId().equals(stateId)) || stateId == null)))
                .findAny().orElse(null);
        return matchingChecklist != null;
    }
}
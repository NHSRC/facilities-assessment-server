package org.nhsrc.service.util;

import org.junit.Ignore;
import org.junit.Test;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.State;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ChecklistMatcherTest {
    @Test @Ignore
    public void checkConflictsForNewEntity() {
        List<Checklist> checklists = new ArrayList<>();
        createChecklist(null, checklists, null);
        assertTrue(ChecklistMatcher.isConflicting(checklists, null, null));
        assertTrue(ChecklistMatcher.isConflicting(checklists, 1, null));

        checklists = new ArrayList<>();
        createChecklist(1, checklists, null);
        createChecklist(2, checklists, null);
        assertTrue(ChecklistMatcher.isConflicting(checklists, null, null));
        assertTrue(ChecklistMatcher.isConflicting(checklists, 1, null));
        assertFalse(ChecklistMatcher.isConflicting(checklists, 3, null));

        checklists = new ArrayList<>();
        createChecklist(null, checklists, null);
        assertTrue(ChecklistMatcher.isConflicting(checklists, null, null));
        assertTrue(ChecklistMatcher.isConflicting(checklists, 1, null));

        checklists = new ArrayList<>();
        createChecklist(1, checklists, null);
        createChecklist(2, checklists, null);
        assertTrue(ChecklistMatcher.isConflicting(checklists, null, null));
        assertTrue(ChecklistMatcher.isConflicting(checklists, 1, null));
        assertFalse(ChecklistMatcher.isConflicting(checklists, 3, null));
    }

    private void createChecklist(Integer stateId, List<Checklist> checklists, Integer checklistId) {
        Checklist checklist = new Checklist();
        checklist.setId(checklistId);
        if (stateId != null) {
            State state = new State();
            state.setId(stateId);
            checklist.setState(state);
        }
        checklist.setName("C1");
        checklists.add(checklist);
    }
}
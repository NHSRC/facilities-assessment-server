package org.nhsrc.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReferencableEntityTest {
    @Test
    public void isConflicting_for_new() {
        ReferencableEntity existing = create("A");
        existing.setId(1);

        assertTrue(ReferencableEntity.isConflicting(existing, create("A")));
    }

    @Test
    public void isConflicting_for_old() {
        ReferencableEntity existing = create("A");
        existing.setId(1);
        ReferencableEntity current = create("A");
        current.setId(1);

        assertFalse(ReferencableEntity.isConflicting(existing, current));
    }

    private ReferencableEntity create(String reference) {
        ReferencableEntity existing = new AreaOfConcern();
        existing.setReference(reference);
        return existing;
    }
}
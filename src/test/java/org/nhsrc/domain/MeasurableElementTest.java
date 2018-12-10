package org.nhsrc.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class MeasurableElementTest {
    @Test
    public void containsCheckpoint() {
        Checklist checklist = new Checklist();
        checklist.setName("clfoo");
        MeasurableElement measurableElement = new MeasurableElement();
        measurableElement.setName("mefoo");
        checklist.addCheckpoint(createCheckpoint("foo", checklist, measurableElement));
        checklist.addCheckpoint(createCheckpoint("bar", checklist, measurableElement));

        MeasurableElement anotherMeasurableElement = new MeasurableElement();
        anotherMeasurableElement.setName("amefoo");
        assertTrue(measurableElement.containsCheckpoint(createCheckpoint("foo", checklist, measurableElement)));
        assertFalse(measurableElement.containsCheckpoint(createCheckpoint("baz", checklist, anotherMeasurableElement)));
    }

    @Test
    public void setRefNumber() {
        assertEquals(1101, createMeasurableElementWithReference("A1.1").getRefAsNumber(), 0.01);
        assertEquals(1102, createMeasurableElementWithReference("A1.2").getRefAsNumber(), 0.01);
        assertEquals(1109, createMeasurableElementWithReference("A1.9").getRefAsNumber(), 0.01);
        assertEquals(1110, createMeasurableElementWithReference("A1.10").getRefAsNumber(), 0.01);
        assertEquals(1111, createMeasurableElementWithReference("A1.11").getRefAsNumber(), 0.01);
        assertEquals(2203, createMeasurableElementWithReference("B2.3").getRefAsNumber(), 0.01);
    }

    private MeasurableElement createMeasurableElementWithReference(String reference) {
        MeasurableElement measurableElement = new MeasurableElement();
        measurableElement.setReference(reference);
        return measurableElement;
    }

    private Checkpoint createCheckpoint(String name, Checklist checklist, MeasurableElement measurableElement) {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setName(name);
        checkpoint.setChecklist(checklist);
        checkpoint.setMeasurableElement(measurableElement);
        measurableElement.addCheckpoint(checkpoint);
        return checkpoint;
    }
}
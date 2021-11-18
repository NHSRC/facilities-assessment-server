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

        Checkpoint cp1 = createCheckpoint("foo", checklist, measurableElement);
        cp1.setChecklist(checklist);
        Checkpoint cp2 = createCheckpoint("bar", checklist, measurableElement);
        cp2.setChecklist(checklist);

        MeasurableElement anotherMeasurableElement = new MeasurableElement();
        anotherMeasurableElement.setName("amefoo");
        assertTrue(measurableElement.containsCheckpoint(cp1));
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

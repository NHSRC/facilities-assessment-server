package org.nhsrc.domain;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(true, measurableElement.containsCheckpoint(createCheckpoint("foo", checklist, measurableElement)));
        Assert.assertEquals(false, measurableElement.containsCheckpoint(createCheckpoint("baz", checklist, anotherMeasurableElement)));
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
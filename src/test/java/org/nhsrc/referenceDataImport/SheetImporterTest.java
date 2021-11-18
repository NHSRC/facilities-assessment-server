package org.nhsrc.referenceDataImport;

import org.junit.Before;
import org.junit.Test;
import org.nhsrc.domain.AssessmentTool;

import static org.junit.Assert.*;

public class SheetImporterTest {
    private AssessmentChecklistData data;

    @Before
    public void setup() {
        this.data = new AssessmentChecklistData(new AssessmentTool());
    }

    @Test
    public void getMERefFromErraticInputs() {
        SheetImporter importer = new SheetImporter(data);
        assertEquals("A3.2", importer.getMERef("ME A3.2"));
        assertEquals("A3.2", importer.getMERef("ME +A3.2"));
        assertEquals("A3.2", importer.getMERef("ME BA3.2"));
        assertEquals("A3.2", importer.getMERef("ME A3..2"));
        assertEquals("A3.2", importer.getMERef("ME A3,2"));
        assertEquals("A3.2", importer.getMERef("ME A3,,2"));
    }
}

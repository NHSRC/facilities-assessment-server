package org.nhsrc.referenceDataImport;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SheetRowImporterTest {
    private AssessmentChecklistData data;

    @Before
    public void setup() {
        this.data = new AssessmentChecklistData();
    }

    @Test
    public void getMERefFromErraticInputs() {
        SheetRowImporter importer = new SheetRowImporter(data);
        assertEquals("A3.2", importer.getMERef("ME A3.2"));
        assertEquals("A3.2", importer.getMERef("ME +A3.2"));
        assertEquals("A3.2", importer.getMERef("ME BA3.2"));
        assertEquals("A3.2", importer.getMERef("ME A3..2"));
        assertEquals("A3.2", importer.getMERef("ME A3,2"));
        assertEquals("A3.2", importer.getMERef("ME A3,,2"));
    }
}

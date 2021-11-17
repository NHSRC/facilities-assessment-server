package org.nhsrc.visitor;

import org.nhsrc.domain.*;
import org.nhsrc.referenceDataImport.GunakExcelFile;

public interface GunakChecklistVisitor {
    void visit(GunakExcelFile gunakExcelFile);
    void visit(Checklist checklist);
    void visit(AreaOfConcern areaOfConcern);

    void visit(Standard standard);
    void visit(MeasurableElement measurableElement);
    void visit(Checkpoint checkpoint);
}

package org.nhsrc.domain.missing;

import org.nhsrc.domain.Checklist;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "missing_checkpoint")
public class MissingCheckpoint extends BaseMissingEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "measurable_element_reference")
    private String measurableElementReference;

    @ManyToOne(targetEntity = Checklist.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    @NotNull
    private Checklist checklist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public String getMeasurableElementReference() {
        return measurableElementReference;
    }

    public void setMeasurableElementReference(String measurableElementReference) {
        this.measurableElementReference = measurableElementReference;
    }
}
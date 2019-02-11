package org.nhsrc.domain.missing;

import org.nhsrc.domain.Checklist;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "missing_checkpoint")
public class MissingCheckpoint implements Persistable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null || id == 0;
    }

    @Column(name = "name", nullable = false)
    private String name;

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
}
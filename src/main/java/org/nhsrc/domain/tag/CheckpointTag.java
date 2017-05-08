package org.nhsrc.domain.tag;

import org.nhsrc.domain.AbstractEntity;
import org.nhsrc.domain.Tag;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "checkpoint_tag")
public class CheckpointTag extends AbstractEntity {
    @JoinColumn(name = "checkpoint_tag_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
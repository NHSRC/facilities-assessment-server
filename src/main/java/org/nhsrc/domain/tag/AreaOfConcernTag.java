package org.nhsrc.domain.tag;

import org.nhsrc.domain.AbstractEntity;
import org.nhsrc.domain.Tag;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "area_of_concern_tag")
public class AreaOfConcernTag extends AbstractEntity {
    @JoinColumn(name = "area_of_concern_tag_id")
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
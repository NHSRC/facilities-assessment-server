package org.nhsrc.domain;

import org.springframework.data.domain.Persistable;

public interface ReferencableEntity extends Persistable<Integer> {
    static boolean isConflicting(ReferencableEntity existing, ReferencableEntity current) {
        return (current.isNew() && existing != null) || (!current.isNew() && existing != null && !existing.getId().equals(current.getId()));
    }

    String getReference();

    void setReference(String reference);

    void setId(Integer id);
}

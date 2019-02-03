package org.nhsrc.domain;

public interface ReferencableEntity {
    static boolean isConflicting(ReferencableEntity existing, ReferencableEntity current) {
        return (current.isNew() && existing != null) || (!current.isNew() && existing != null && !existing.getId().equals(current.getId()));
    }

    String getReference();

    void setReference(String reference);

    Integer getId();

    boolean isNew();

    void setId(Integer id);
}

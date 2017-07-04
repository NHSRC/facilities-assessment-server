package org.nhsrc.dto;

import javax.persistence.*;

@Entity
public class ChecklistProgressDTO {
    protected String uuid;
    private int completed;
    private int total;

    public ChecklistProgressDTO() {
    }

    public ChecklistProgressDTO(String uuid, int completed, int total) {
        this.uuid = uuid;
        this.completed = completed;
        this.total = total;
    }

    @Id
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "EntityProgressDTO{" +
                "uuid='" + uuid + '\'' +
                ", completed=" + completed +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChecklistProgressDTO that = (ChecklistProgressDTO) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
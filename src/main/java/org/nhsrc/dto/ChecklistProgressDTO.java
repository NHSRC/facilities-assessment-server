package org.nhsrc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ChecklistProgressDTO {
    private int id;
    protected String uuid;
    private int completed;
    private int total;
    private boolean aocFilled;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
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

    @Transient
    @JsonIgnore
    public boolean isAocFilled() {
        return aocFilled;
    }

    @Transient
    public void setAocFilled(boolean aocFilled) {
        this.aocFilled = aocFilled;
    }
}
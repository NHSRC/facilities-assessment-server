package org.nhsrc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
public class AreaOfConcernProgressDTO implements Serializable {
    private String checklistUUID;
    private int checklistId;
    protected String uuid;
    private int areaOfConcernId;
    private int completed;
    private int total;
    private boolean anyStandardScored;
    private int id;

    public AreaOfConcernProgressDTO(String uuid, int completed, int total, String checklistUUID) {
        this.checklistUUID = checklistUUID;
        this.uuid = uuid;
        this.completed = completed;
        this.total = total;
    }

    public AreaOfConcernProgressDTO() {
    }

    @Id
    public String getChecklistUUID() {
        return checklistUUID;
    }

    public void setChecklistUUID(String checklistUUID) {
        this.checklistUUID = checklistUUID;
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

    public int getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }

    public int getAreaOfConcernId() {
        return areaOfConcernId;
    }

    public void setAreaOfConcernId(int areaOfConcernId) {
        this.areaOfConcernId = areaOfConcernId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaOfConcernProgressDTO that = (AreaOfConcernProgressDTO) o;
        return checklistUUID.equals(that.checklistUUID) && uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + checklistUUID.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AreaOfConcernProgressDTO{" +
                "checklistUUID='" + checklistUUID + '\'' +
                ", uuid='" + uuid + '\'' +
                ", completed=" + completed +
                ", total=" + total +
                '}';
    }

    @Transient
    @JsonIgnore
    public boolean isAnyStandardScored() {
        return anyStandardScored;
    }

    @Transient
    public void setAnyStandardScored(boolean anyStandardScored) {
        this.anyStandardScored = anyStandardScored;
    }
}
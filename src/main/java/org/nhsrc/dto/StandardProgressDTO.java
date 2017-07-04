package org.nhsrc.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StandardProgressDTO {
    private String aocUUID;
    private String checklistUUID;
    protected String uuid;
    private int completed;
    private int total;

    public StandardProgressDTO(String uuid, int completed, int total, String checklistUUID, String aocUUID) {
        this.aocUUID = aocUUID;
        this.checklistUUID = checklistUUID;
        this.uuid = uuid;
        this.completed = completed;
        this.total = total;
    }

    public StandardProgressDTO() {
    }

    public String getAocUUID() {
        return aocUUID;
    }

    public void setAocUUID(String aocUUID) {
        this.aocUUID = aocUUID;
    }

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
}
package org.nhsrc.data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StandardTotal {
    private String checklistUUID;
    private String uuid;
    private int total;

    private String aocUUID;

    public StandardTotal(String checklistUUID, String uuid, int total, String aocUUID) {
        this.checklistUUID = checklistUUID;
        this.uuid = uuid;
        this.total = total;
        this.aocUUID = aocUUID;
    }

    public StandardTotal() {
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
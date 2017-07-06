package org.nhsrc.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class AreaOfConcernTotal implements Serializable {
    private String checklistUUID;
    private String uuid;
    private int total;

    public AreaOfConcernTotal(String checklistUUID, String uuid, int total) {
        this.checklistUUID = checklistUUID;
        this.uuid = uuid;
        this.total = total;
    }

    public AreaOfConcernTotal() {
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "AreaOfConcernTotal{" +
                "checklistUUID='" + checklistUUID + '\'' +
                ", uuid='" + uuid + '\'' +
                ", total=" + total +
                '}';
    }
}
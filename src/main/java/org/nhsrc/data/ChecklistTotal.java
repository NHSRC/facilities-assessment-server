package org.nhsrc.data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ChecklistTotal {
    private String uuid;
    private int total;

    public ChecklistTotal() {
    }

    public ChecklistTotal(String uuid, int total) {
        this.uuid = uuid;
        this.total = total;
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
        return "ChecklistEntityTotal{" +
                "uuid='" + uuid + '\'' +
                ", total=" + total +
                '}';
    }
}
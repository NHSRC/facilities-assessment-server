package org.nhsrc.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class StandardProgressDTO implements Serializable {
    private long facilityAssessmentId;
    private String aocUUID;
    private String checklistUUID;
    protected String uuid;
    private int completed;
    private int total;

    public StandardProgressDTO(long facilityAssessmentId, String aocUUID, String checklistUUID, String uuid, int completed, int total) {
        this.facilityAssessmentId = facilityAssessmentId;
        this.aocUUID = aocUUID;
        this.checklistUUID = checklistUUID;
        this.uuid = uuid;
        this.completed = completed;
        this.total = total;
    }

    public StandardProgressDTO() {
    }

    @Id
    public String getAocUUID() {
        return aocUUID;
    }

    public void setAocUUID(String aocUUID) {
        this.aocUUID = aocUUID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StandardProgressDTO that = (StandardProgressDTO) o;

        if (!aocUUID.equals(that.aocUUID)) return false;
        if (!checklistUUID.equals(that.checklistUUID)) return false;
        if (facilityAssessmentId != that.facilityAssessmentId) return false;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        int result = aocUUID.hashCode();
        result = 31 * result + checklistUUID.hashCode();
        result = 31 * result + uuid.hashCode();
        return result;
    }

    @Id
    public long getFacilityAssessmentId() {
        return facilityAssessmentId;
    }

    public void setFacilityAssessmentId(long facilityAssessmentId) {
        this.facilityAssessmentId = facilityAssessmentId;
    }
}
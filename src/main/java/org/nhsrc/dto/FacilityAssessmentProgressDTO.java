package org.nhsrc.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacilityAssessmentProgressDTO {
    private String uuid;
    private List<ChecklistProgressDTO> checklistsProgress = new ArrayList<>();
    private List<AreaOfConcernProgressDTO> areaOfConcernsProgress = new ArrayList<>();
    private List<StandardProgressDTO> standardsProgress = new ArrayList<>();
    private java.util.Date lastModifiedDate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<ChecklistProgressDTO> getChecklistsProgress() {
        return checklistsProgress;
    }

    public void setChecklistsProgress(List<ChecklistProgressDTO> checklistsProgress) {
        this.checklistsProgress = checklistsProgress;
    }

    public List<AreaOfConcernProgressDTO> getAreaOfConcernsProgress() {
        return areaOfConcernsProgress;
    }

    public void setAreaOfConcernsProgress(List<AreaOfConcernProgressDTO> areaOfConcernsProgress) {
        this.areaOfConcernsProgress = areaOfConcernsProgress;
    }

    public List<StandardProgressDTO> getStandardsProgress() {
        return standardsProgress;
    }

    public void setStandardsProgress(List<StandardProgressDTO> standardsProgress) {
        this.standardsProgress = standardsProgress;
    }

    public void addChecklistProgress(ChecklistProgressDTO checklistProgressDTO) {
        this.checklistsProgress.add(checklistProgressDTO);
    }

    public void addAreaOfConcernProgress(AreaOfConcernProgressDTO areaOfConcernProgressDTO) {
        this.areaOfConcernsProgress.add(areaOfConcernProgressDTO);
    }

    public void addStandardProgress(StandardProgressDTO standardProgressDTO) {
        this.standardsProgress.add(standardProgressDTO);
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
package org.nhsrc.dto;

import org.nhsrc.domain.CheckpointScore;

import java.util.List;
import java.util.UUID;

public class ChecklistDTO {
    private UUID uuid;

    private String name;

    private UUID department;

    private UUID facilityAssessment;

    private List<CheckpointScoreDTO> checkpointScores;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getDepartment() {
        return department;
    }

    public void setDepartment(UUID department) {
        this.department = department;
    }

    public UUID getFacilityAssessment() {
        return facilityAssessment;
    }

    public void setFacilityAssessment(UUID facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }

    public List<CheckpointScoreDTO> getCheckpointScores() {
        return checkpointScores;
    }

    public void setCheckpointScores(List<CheckpointScoreDTO> checkpointScores) {
        this.checkpointScores = checkpointScores;
    }

    public void addCheckpointScore(CheckpointScoreDTO checkpointScore) {
        this.checkpointScores.add(checkpointScore);
    }
}

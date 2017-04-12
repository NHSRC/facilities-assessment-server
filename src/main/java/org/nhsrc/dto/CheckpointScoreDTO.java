package org.nhsrc.dto;

import java.util.UUID;

public class CheckpointScoreDTO {
    private UUID uuid;

    private UUID checkpoint;

    private Integer score;

    private String remarks;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(UUID checkpoint) {
        this.checkpoint = checkpoint;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

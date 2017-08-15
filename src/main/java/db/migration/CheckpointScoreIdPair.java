package db.migration;

public class CheckpointScoreIdPair {
    private int checkpointId;
    private int checkpointScoreId;

    public CheckpointScoreIdPair() {
    }

    public CheckpointScoreIdPair(int checkpointId, int checkpointScoreId) {
        this.checkpointId = checkpointId;
        this.checkpointScoreId = checkpointScoreId;
    }

    public int getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(int checkpointId) {
        this.checkpointId = checkpointId;
    }

    public int getCheckpointScoreId() {
        return checkpointScoreId;
    }

    public void setCheckpointScoreId(int checkpointScoreId) {
        this.checkpointScoreId = checkpointScoreId;
    }

    @Override
    public String toString() {
        return "CheckpointScoreIdPair{" +
                "checkpointId=" + checkpointId +
                ", checkpointScoreId=" + checkpointScoreId +
                '}';
    }
}
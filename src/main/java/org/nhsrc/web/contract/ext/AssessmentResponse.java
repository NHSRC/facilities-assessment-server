package org.nhsrc.web.contract.ext;

import java.util.ArrayList;
import java.util.List;

public class AssessmentResponse extends AssessmentSummaryResponse {
    private List<ChecklistAssessment> checklists = new ArrayList<>();

    public static class CheckpointAssessment {
        private String checkpoint;
        private int score;
        private boolean markedNotApplicable;
        private String remarks;

        public String getCheckpoint() {
            return checkpoint;
        }

        public void setCheckpoint(String checkpoint) {
            this.checkpoint = checkpoint;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public boolean isMarkedNotApplicable() {
            return markedNotApplicable;
        }

        public void setMarkedNotApplicable(boolean markedNotApplicable) {
            this.markedNotApplicable = markedNotApplicable;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }

    public static class ChecklistAssessment {
        private String name;

        public ChecklistAssessment(String name) {
            this.name = name;
        }

        private List<AreaOfConcernAssessment> areaOfConcerns = new ArrayList<>();

        public String getName() {
            return name;
        }

        public List<AreaOfConcernAssessment> getAreaOfConcerns() {
            return areaOfConcerns;
        }
    }

    public static class AreaOfConcernAssessment {
        private String reference;
        private List<StandardAssessment> standards = new ArrayList<>();

        public AreaOfConcernAssessment(String reference) {
            this.reference = reference;
        }

        public String getReference() {
            return reference;
        }

        public List<StandardAssessment> getStandards() {
            return standards;
        }
    }

    public static class StandardAssessment {
        private String reference;
        private List<MeasurableElementAssessment> measurableElements = new ArrayList<>();

        public StandardAssessment(String reference) {
            this.reference = reference;
        }

        public String getReference() {
            return reference;
        }

        public List<MeasurableElementAssessment> getMeasurableElements() {
            return measurableElements;
        }
    }

    public static class MeasurableElementAssessment {
        private String reference;
        private List<CheckpointAssessment> checkpointAssessments = new ArrayList<>();

        public MeasurableElementAssessment(String reference) {
            this.reference = reference;
        }

        public String getReference() {
            return reference;
        }

        public List<CheckpointAssessment> getCheckpointAssessments() {
            return checkpointAssessments;
        }

        public void addCheckpointAssessment(CheckpointAssessment checkpointAssessment) {
            checkpointAssessments.add(checkpointAssessment);
        }
    }

    public List<ChecklistAssessment> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<ChecklistAssessment> checklists) {
        this.checklists = checklists;
    }
}
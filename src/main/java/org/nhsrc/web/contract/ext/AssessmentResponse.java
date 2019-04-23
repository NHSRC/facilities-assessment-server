package org.nhsrc.web.contract.ext;

import java.util.ArrayList;
import java.util.List;

public class AssessmentResponse extends AssessmentSummaryResponse {
    private List<ChecklistAssessment> checklists = new ArrayList<>();
    private int numberOfChecklists;
    private int totalNumberOfScoredCheckpoints;

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
        private int numberOfAreaOfConcerns;

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

        public int getNumberOfAreaOfConcerns() {
            return numberOfAreaOfConcerns;
        }

        public void setNumberOfAreaOfConcerns(int numberOfAreaOfConcerns) {
            this.numberOfAreaOfConcerns = numberOfAreaOfConcerns;
        }

        public void updateCounts() {
            this.numberOfAreaOfConcerns = this.getAreaOfConcerns().size();
            this.getAreaOfConcerns().forEach(AreaOfConcernAssessment::updateCounts);
        }
    }

    public static class AreaOfConcernAssessment {
        private String reference;
        private int numberOfStandards;
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

        public int getNumberOfStandards() {
            return numberOfStandards;
        }

        public void setNumberOfStandards(int numberOfStandards) {
            this.numberOfStandards = numberOfStandards;
        }

        public void updateCounts() {
            this.numberOfStandards = this.getStandards().size();
            this.standards.forEach(StandardAssessment::updateCounts);
        }
    }

    public static class StandardAssessment {
        private String reference;
        private int numberOfMeasurableElements;
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

        public int getNumberOfMeasurableElements() {
            return numberOfMeasurableElements;
        }

        public void setNumberOfMeasurableElements(int numberOfMeasurableElements) {
            this.numberOfMeasurableElements = numberOfMeasurableElements;
        }

        public void updateCounts() {
            this.numberOfMeasurableElements = this.getMeasurableElements().size();
            this.measurableElements.forEach(MeasurableElementAssessment::updateCounts);
        }
    }

    public static class MeasurableElementAssessment {
        private String reference;
        private List<CheckpointAssessment> checkpointAssessments = new ArrayList<>();
        private int numberOfCheckpoints;

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

        public int getNumberOfCheckpoints() {
            return numberOfCheckpoints;
        }

        public void updateCounts() {
            this.numberOfCheckpoints = this.checkpointAssessments.size();
        }
    }

    public List<ChecklistAssessment> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<ChecklistAssessment> checklists) {
        this.checklists = checklists;
    }

    public int getNumberOfChecklists() {
        return numberOfChecklists;
    }

    public void updateCounts(int size) {
        this.checklists.forEach(ChecklistAssessment::updateCounts);
        this.numberOfChecklists = this.checklists.size();
        this.totalNumberOfScoredCheckpoints = size;
    }

    public int getTotalNumberOfScoredCheckpoints() {
        return totalNumberOfScoredCheckpoints;
    }
}
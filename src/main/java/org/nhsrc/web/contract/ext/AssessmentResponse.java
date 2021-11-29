package org.nhsrc.web.contract.ext;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.AssessmentToolType;
import org.nhsrc.domain.CheckpointScore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssessmentResponse extends AssessmentSummaryResponse {
    private List<ChecklistAssessment> checklists = new ArrayList<>();
    private List<IndicatorAssessment> indicators = new ArrayList<>();
    private Integer numberOfIndicators;
    private Integer numberOfChecklists;
    private Integer totalNumberOfScoredCheckpoints;
    private List<AreaOfConcernAssessmentScore> areaOfConcernScores;

    public static AssessmentResponse createNew(AssessmentTool assessmentTool) {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        // The response is polymorphic in nature, for API UX set values to null so that they do not show up in the response body as empty/0
        if (assessmentTool.getAssessmentToolType().equals(AssessmentToolType.INDICATOR)) {
            assessmentResponse.checklists = null;
            assessmentResponse.numberOfChecklists = assessmentResponse.totalNumberOfScoredCheckpoints = null;
        } else {
            assessmentResponse.indicators = null;
            assessmentResponse.numberOfIndicators = null;
        }

        return assessmentResponse;
    }

    public AreaOfConcernAssessmentScore addAreaOfConcernScore(String reference, int score) {
        AreaOfConcernAssessmentScore areaOfConcernAssessmentScore = new AreaOfConcernAssessmentScore();
        areaOfConcernAssessmentScore.setScore(score);
        areaOfConcernAssessmentScore.setReference(reference);
        areaOfConcernScores.add(new AreaOfConcernAssessmentScore(reference, score));
        return areaOfConcernAssessmentScore;
    }

    public static class IndicatorAssessment {
        private String name;
        private String dataType;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

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
        private int numberOfCheckpoints;
        private List<AreaOfConcernAssessment> areaOfConcerns = new ArrayList<>();
        private double score;

        public ChecklistAssessment(String name) {
            this.name = name;
        }

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

        public int getNumberOfCheckpoints() {
            return numberOfCheckpoints;
        }

        public void updateCounts() {
            this.numberOfAreaOfConcerns = this.getAreaOfConcerns().size();
            this.getAreaOfConcerns().forEach(AreaOfConcernAssessment::updateCounts);
            this.numberOfCheckpoints = this.getAreaOfConcerns().stream().map(AreaOfConcernAssessment::getCheckpointCount).reduce((a, b) -> a + b).get();
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getScore() {
            return score;
        }
    }

    public static class AreaOfConcernAssessmentScore {
        private String reference;
        private int score;
        private Map<String, Integer> standardScores = new HashMap<>();

        public AreaOfConcernAssessmentScore() {
        }

        public AreaOfConcernAssessmentScore(String reference, int score) {
            this.reference = reference;
            this.score = score;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public double getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void addStandardScore(String reference, int score) {
            this.standardScores.put(reference, score);
        }

        public Map<String, Integer> getStandardScores() {
            return standardScores;
        }
    }

    public static class AreaOfConcernAssessment {
        private String reference;
        private int numberOfStandards;
        private List<StandardAssessment> standards = new ArrayList<>();
        private int score;

        public AreaOfConcernAssessment() {
        }

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

        public int getCheckpointCount() {
            return this.getStandards().stream().map(StandardAssessment::getCheckpointCount).reduce(Integer::sum).get();
        }
    }

    public static class StandardAssessment {
        private String reference;
        private int score;
        private int numberOfMeasurableElements;
        private final List<MeasurableElementAssessment> measurableElements = new ArrayList<>();

        public StandardAssessment() {
        }

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

        public int getCheckpointCount() {
            return this.getMeasurableElements().stream().map(MeasurableElementAssessment::getNumberOfCheckpoints).reduce(Integer::sum).get();
        }
    }

    public static class MeasurableElementAssessment {
        private String reference;
        private final List<CheckpointAssessment> checkpointAssessments = new ArrayList<>();
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

    public Integer getNumberOfChecklists() {
        return numberOfChecklists;
    }

    public void updateCounts(List<CheckpointScore> scores) {
        this.checklists.forEach(ChecklistAssessment::updateCounts);
        this.numberOfChecklists = this.checklists.size();
        this.totalNumberOfScoredCheckpoints = scores.size();
    }

    public Integer getTotalNumberOfScoredCheckpoints() {
        return totalNumberOfScoredCheckpoints;
    }

    public List<IndicatorAssessment> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<IndicatorAssessment> indicators) {
        this.indicators = indicators;
    }

    public Integer getNumberOfIndicators() {
        return numberOfIndicators;
    }

    public void setNumberOfIndicators(Integer numberOfIndicators) {
        this.numberOfIndicators = numberOfIndicators;
    }
}

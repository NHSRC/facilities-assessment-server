package org.nhsrc.web.contract.ext;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private final List<AreaOfConcernAssessmentScore> areaOfConcernScores = new ArrayList<>();

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

    public List<AreaOfConcernAssessmentScore> getAreaOfConcernScores() {
        return areaOfConcernScores;
    }

    public AreaOfConcernAssessmentScore addAreaOfConcernScore(String uuid, int score) {
        AreaOfConcernAssessmentScore areaOfConcernAssessmentScore = new AreaOfConcernAssessmentScore(uuid, score);
        areaOfConcernScores.add(areaOfConcernAssessmentScore);
        return areaOfConcernAssessmentScore;
    }

    public static class IndicatorAssessment {
        private String systemId;
        private String indicatorDefinition;
        private String value;

        public String getSystemId() {
            return systemId;
        }

        public void setSystemId(String systemId) {
            this.systemId = systemId;
        }

        public String getIndicatorDefinition() {
            return indicatorDefinition;
        }

        public void setIndicatorDefinition(String indicatorDefinition) {
            this.indicatorDefinition = indicatorDefinition;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class CheckpointAssessment {
        private String systemId;
        private int score;
        private boolean markedNotApplicable;
        private String remarks;

        public String getSystemId() {
            return systemId;
        }

        public void setSystemId(String systemId) {
            this.systemId = systemId;
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
        private int checklistId;
        private String systemId;
        private int numberOfAreaOfConcerns;
        private int numberOfCheckpoints;
        private List<AreaOfConcernAssessment> areaOfConcerns = new ArrayList<>();
        private double score;

        public ChecklistAssessment(String systemId, int checklistId) {
            this.systemId = systemId;
            this.checklistId = checklistId;
        }

        @JsonIgnore
        public int getChecklistId() {
            return checklistId;
        }

        public String getSystemId() {
            return systemId;
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
        private String systemId;
        private int score;
        private Map<String, Integer> standardScores = new HashMap<>();

        public AreaOfConcernAssessmentScore() {
        }

        public AreaOfConcernAssessmentScore(String systemId, int score) {
            this.systemId = systemId;
            this.score = score;
        }

        public String getSystemId() {
            return systemId;
        }

        public void setSystemId(String systemId) {
            this.systemId = systemId;
        }

        public double getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void addStandardScore(String uuid, int score) {
            this.standardScores.put(uuid, score);
        }

        public Map<String, Integer> getStandardScores() {
            return standardScores;
        }
    }

    public static class AreaOfConcernAssessment {
        private String systemId;
        private int numberOfStandards;
        private List<StandardAssessment> standards = new ArrayList<>();
        private int score;

        public AreaOfConcernAssessment() {
        }

        public AreaOfConcernAssessment(String systemId) {
            this.systemId = systemId;
        }

        public String getSystemId() {
            return systemId;
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
        private String systemId;
        private int score;
        private int numberOfMeasurableElements;
        private final List<MeasurableElementAssessment> measurableElements = new ArrayList<>();

        public StandardAssessment() {
        }

        public StandardAssessment(String systemId) {
            this.systemId = systemId;
        }

        public String getSystemId() {
            return systemId;
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
        private String systemId;
        private final List<CheckpointAssessment> checkpoints = new ArrayList<>();
        private int numberOfCheckpoints;

        public MeasurableElementAssessment(String systemId) {
            this.systemId = systemId;
        }

        public String getSystemId() {
            return systemId;
        }

        public List<CheckpointAssessment> getCheckpoints() {
            return checkpoints;
        }

        public void addCheckpointAssessment(CheckpointAssessment checkpointAssessment) {
            checkpoints.add(checkpointAssessment);
        }

        public int getNumberOfCheckpoints() {
            return numberOfCheckpoints;
        }

        public void updateCounts() {
            this.numberOfCheckpoints = this.checkpoints.size();
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

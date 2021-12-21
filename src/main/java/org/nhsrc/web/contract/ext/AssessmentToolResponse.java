package org.nhsrc.web.contract.ext;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssessmentToolResponse {
    private String externalId;
    private String name;
    private String programName;
    private String assessmentToolType;
    private List<ChecklistResponse> checklists = new ArrayList<>();
    private String state;
    private boolean universal;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isUniversal() {
        return universal;
    }

    public void setUniversal(boolean universal) {
        this.universal = universal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssessmentToolType() {
        return assessmentToolType;
    }

    public void setAssessmentToolType(String assessmentToolType) {
        this.assessmentToolType = assessmentToolType;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public List<ChecklistResponse> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<ChecklistResponse> checklists) {
        this.checklists = checklists;
    }

    public static class ChecklistResponse {
        private String externalId;
        private String name;
        private String reference;
        private List<AreaOfConcernResponse> areaOfConcerns = new ArrayList<>();

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public List<AreaOfConcernResponse> getAreaOfConcerns() {
            return areaOfConcerns;
        }

        public void setAreaOfConcerns(List<AreaOfConcernResponse> areaOfConcerns) {
            this.areaOfConcerns = areaOfConcerns;
        }
    }

    public static class AreaOfConcernResponse {
        private String externalId;
        private String name;
        private String reference;
        private List<StandardResponse> standards = new ArrayList<>();

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public List<StandardResponse> getStandards() {
            return standards;
        }

        public void setStandards(List<StandardResponse> standards) {
            this.standards = standards;
        }
    }

    public static class StandardResponse {
        private String externalId;
        private String name;
        private String reference;
        private List<MeasurableElementResponse> measurableElements = new ArrayList<>();

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public List<MeasurableElementResponse> getMeasurableElements() {
            return measurableElements;
        }

        public void setMeasurableElements(List<MeasurableElementResponse> measurableElements) {
            this.measurableElements = measurableElements;
        }
    }

    public static class MeasurableElementResponse {
        private String externalId;
        private String name;
        private String reference;
        private List<CheckpointResponse> checkpoints = new ArrayList<>();

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public List<CheckpointResponse> getCheckpoints() {
            return checkpoints;
        }

        public void setCheckpoints(List<CheckpointResponse> checkpoints) {
            this.checkpoints = checkpoints;
        }
    }

    public static class CheckpointResponse {
        private String uuid;
        private String name;
        private String meansOfVerification;
        private boolean assessmentMethodByObservation;
        private boolean assessmentMethodByStaffInterview;
        private boolean assessmentMethodByPatientInterview;
        private boolean assessmentMethodByRecordReview;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMeansOfVerification() {
            return meansOfVerification;
        }

        public void setMeansOfVerification(String meansOfVerification) {
            this.meansOfVerification = meansOfVerification;
        }

        public boolean isAssessmentMethodByObservation() {
            return assessmentMethodByObservation;
        }

        public void setAssessmentMethodByObservation(boolean assessmentMethodByObservation) {
            this.assessmentMethodByObservation = assessmentMethodByObservation;
        }

        public boolean isAssessmentMethodByStaffInterview() {
            return assessmentMethodByStaffInterview;
        }

        public void setAssessmentMethodByStaffInterview(boolean assessmentMethodByStaffInterview) {
            this.assessmentMethodByStaffInterview = assessmentMethodByStaffInterview;
        }

        public boolean isAssessmentMethodByPatientInterview() {
            return assessmentMethodByPatientInterview;
        }

        public void setAssessmentMethodByPatientInterview(boolean assessmentMethodByPatientInterview) {
            this.assessmentMethodByPatientInterview = assessmentMethodByPatientInterview;
        }

        public boolean isAssessmentMethodByRecordReview() {
            return assessmentMethodByRecordReview;
        }

        public void setAssessmentMethodByRecordReview(boolean assessmentMethodByRecordReview) {
            this.assessmentMethodByRecordReview = assessmentMethodByRecordReview;
        }
    }
}

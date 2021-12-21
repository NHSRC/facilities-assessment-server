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
    private boolean inactive;

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

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

    public void addChecklist(ChecklistResponse checklist) {
        this.checklists.add(checklist);
    }

    public static class BaseToolComponent {
        private String systemId;
        private String name;
        private boolean inactive;

        public String getSystemId() {
            return systemId;
        }

        public void setSystemId(String systemId) {
            this.systemId = systemId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isInactive() {
            return inactive;
        }

        public void setInactive(boolean inactive) {
            this.inactive = inactive;
        }
    }

    public static class BaseToolReferenceComponent extends BaseToolComponent {
        private String reference;

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }
    }

    public static class ChecklistResponse extends BaseToolComponent {
        private List<AreaOfConcernResponse> areaOfConcerns = new ArrayList<>();

        public List<AreaOfConcernResponse> getAreaOfConcerns() {
            return areaOfConcerns;
        }

        public void setAreaOfConcerns(List<AreaOfConcernResponse> areaOfConcerns) {
            this.areaOfConcerns = areaOfConcerns;
        }

        public void addAreaOfConcern(AreaOfConcernResponse aocResponse) {
            this.areaOfConcerns.add(aocResponse);
        }
    }

    public static class AreaOfConcernResponse extends BaseToolReferenceComponent {
        private List<StandardResponse> standards = new ArrayList<>();

        public List<StandardResponse> getStandards() {
            return standards;
        }

        public void setStandards(List<StandardResponse> standards) {
            this.standards = standards;
        }

        public void addStandard(StandardResponse stdResponse) {
            this.standards.add(stdResponse);
        }
    }

    public static class StandardResponse extends BaseToolReferenceComponent {
        private List<MeasurableElementResponse> measurableElements = new ArrayList<>();

        public List<MeasurableElementResponse> getMeasurableElements() {
            return measurableElements;
        }

        public void setMeasurableElements(List<MeasurableElementResponse> measurableElements) {
            this.measurableElements = measurableElements;
        }

        public void addMeasurableElement(MeasurableElementResponse meResponse) {
            this.measurableElements.add(meResponse);
        }
    }

    public static class MeasurableElementResponse extends BaseToolReferenceComponent {
        private List<CheckpointResponse> checkpoints = new ArrayList<>();

        public List<CheckpointResponse> getCheckpoints() {
            return checkpoints;
        }

        public void setCheckpoints(List<CheckpointResponse> checkpoints) {
            this.checkpoints = checkpoints;
        }

        public void addCheckpoint(CheckpointResponse checkpointResponse) {
            this.checkpoints.add(checkpointResponse);
        }
    }

    public static class CheckpointResponse extends BaseToolComponent {
        private String meansOfVerification;
        private boolean byObservation;
        private boolean byStaffInterview;
        private boolean byPatientInterview;
        private boolean byRecordReview;

        public String getMeansOfVerification() {
            return meansOfVerification;
        }

        public void setMeansOfVerification(String meansOfVerification) {
            this.meansOfVerification = meansOfVerification;
        }

        public boolean isByObservation() {
            return byObservation;
        }

        public void setByObservation(boolean byObservation) {
            this.byObservation = byObservation;
        }

        public boolean isByStaffInterview() {
            return byStaffInterview;
        }

        public void setByStaffInterview(boolean byStaffInterview) {
            this.byStaffInterview = byStaffInterview;
        }

        public boolean isByPatientInterview() {
            return byPatientInterview;
        }

        public void setByPatientInterview(boolean byPatientInterview) {
            this.byPatientInterview = byPatientInterview;
        }

        public boolean isByRecordReview() {
            return byRecordReview;
        }

        public void setByRecordReview(boolean byRecordReview) {
            this.byRecordReview = byRecordReview;
        }
    }
}

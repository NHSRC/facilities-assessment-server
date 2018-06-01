package org.nhsrc.web.contract;

import org.nhsrc.domain.FacilityAssessment;

import java.util.ArrayList;
import java.util.List;

public class FacilityAssessmentImportResponse {
    private FacilityAssessment facilityAssessment;
    private List<CheckpointInError> checkpointInErrors = new ArrayList<>();

    public FacilityAssessment getFacilityAssessment() {
        return facilityAssessment;
    }

    public void setFacilityAssessment(FacilityAssessment facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }

    public List<CheckpointInError> getCheckpointInErrors() {
        return checkpointInErrors;
    }

    public void addCheckpointInError(CheckpointInError checkpointInError) {
        checkpointInErrors.add(checkpointInError);
    }

    public static class CheckpointInError {
        private String checkpoint;
        private String measurableElementReference;

        public CheckpointInError() {
        }

        public CheckpointInError(String checkpoint, String measurableElementReference) {
            this.checkpoint = checkpoint;
            this.measurableElementReference = measurableElementReference;
        }

        public String getCheckpoint() {
            return checkpoint;
        }

        public void setCheckpoint(String checkpoint) {
            this.checkpoint = checkpoint;
        }

        public String getMeasurableElementReference() {
            return measurableElementReference;
        }

        public void setMeasurableElementReference(String measurableElementReference) {
            this.measurableElementReference = measurableElementReference;
        }
    }
}
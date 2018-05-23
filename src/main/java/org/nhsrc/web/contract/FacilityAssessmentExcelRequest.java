package org.nhsrc.web.contract;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FacilityAssessmentExcelRequest {
    private UUID facilityUuid;
    private String facilityName;
    private UUID assessmentToolUuid;
    private UUID assessmentTypeUuid;
    private MultipartFile assessmentFile;
    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public MultipartFile getAssessmentFile() {
        return assessmentFile;
    }

    public void setAssessmentFile(MultipartFile assessmentFile) {
        this.assessmentFile = assessmentFile;
    }

    public UUID getFacilityUuid() {
        return facilityUuid;
    }

    public void setFacilityUuid(UUID facilityUuid) {
        this.facilityUuid = facilityUuid;
    }

    public UUID getAssessmentToolUuid() {
        return assessmentToolUuid;
    }

    public void setAssessmentToolUuid(UUID assessmentToolUuid) {
        this.assessmentToolUuid = assessmentToolUuid;
    }

    public UUID getAssessmentTypeUuid() {
        return assessmentTypeUuid;
    }

    public void setAssessmentTypeUuid(UUID assessmentTypeUuid) {
        this.assessmentTypeUuid = assessmentTypeUuid;
    }
}
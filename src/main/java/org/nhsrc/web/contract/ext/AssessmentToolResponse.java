package org.nhsrc.web.contract.ext;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssessmentToolResponse {
    private String externalId;
    private String name;
    private String program;
    private String assessmentToolType;
    private List<ChecklistResponse> checklists = new ArrayList<>();
    private List<IndicatorResponse> indicators = new ArrayList<>();
    private String state;
    private boolean universal;
    private boolean inactive;
    private Date lastModifiedDate;

    public List<IndicatorResponse> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<IndicatorResponse> indicators) {
        this.indicators = indicators;
    }

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

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
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

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public static class BaseToolComponent {
        private String systemId;
        private String name;
        private boolean inactive;
        private java.util.Date lastModifiedDate;
        private java.util.Date createdDate;

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

        public Date getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(Date lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public Date getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Date createdDate) {
            this.createdDate = createdDate;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BaseToolReferenceComponent extends BaseToolComponent {
        private String reference;

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ChecklistResponse extends BaseToolComponent {
        private List<String> areaOfConcerns = new ArrayList<>();
        private String assessmentTool;

        public List<String> getAreaOfConcerns() {
            return areaOfConcerns;
        }

        public void setAreaOfConcerns(List<String> areaOfConcerns) {
            this.areaOfConcerns = areaOfConcerns;
        }

        public void setAssessmentTool(String assessmentTool) {
            this.assessmentTool = assessmentTool;
        }

        public String getAssessmentTool() {
            return assessmentTool;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class IndicatorResponse extends BaseToolComponent {
        private String name;
        private String description;
        private String dataType;
        private String codedValues;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getCodedValues() {
            return codedValues;
        }

        public void setCodedValues(String codedValues) {
            this.codedValues = codedValues;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AreaOfConcernResponse extends BaseToolReferenceComponent {
        private List<String> checklists;

        public void setChecklists(List<String> checklists) {
            this.checklists = checklists;
        }

        public List<String> getChecklists() {
            return checklists;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StandardResponse extends BaseToolReferenceComponent {
        private String areaOfConcern;

        public void setAreaOfConcern(String areaOfConcern) {
            this.areaOfConcern = areaOfConcern;
        }

        public String getAreaOfConcern() {
            return areaOfConcern;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MeasurableElementResponse extends BaseToolReferenceComponent {
        private String standard;

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getStandard() {
            return standard;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CheckpointResponse extends BaseToolComponent {
        private String meansOfVerification;
        private boolean byObservation;
        private boolean byStaffInterview;
        private boolean byPatientInterview;
        private boolean byRecordReview;
        private String measurableElement;
        private Integer sortOrder;

        public String getMeasurableElement() {
            return measurableElement;
        }

        public void setMeasurableElement(String measurableElementUuid) {
            this.measurableElement = measurableElementUuid;
        }

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

        public void setSortOrder(Integer sortOrder) {
            this.sortOrder = sortOrder;
        }

        public Integer getSortOrder() {
            return sortOrder;
        }
    }
}

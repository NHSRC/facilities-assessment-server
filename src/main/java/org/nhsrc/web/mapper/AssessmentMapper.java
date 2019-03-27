package org.nhsrc.web.mapper;

import org.nhsrc.domain.*;
import org.nhsrc.web.contract.ext.AssessmentSummaryResponse;

public class AssessmentMapper {
    public static AssessmentSummaryResponse map(AssessmentSummaryResponse summary, FacilityAssessment source, AssessmentTool assessmentTool) {
        summary.setAssessmentEndDate(source.getEndDate());
        summary.setAssessmentSeries(source.getSeriesName());
        summary.setAssessmentStartDate(source.getStartDate());
        summary.setAssessmentTool(assessmentTool.getName());
        summary.setAssessmentType(source.getAssessmentType().getName());
        District district = source.getDistrict();
        summary.setDistrict(district == null ? "" : district.getName());
        State state = source.getState();
        summary.setState(state == null ? "" : state.getName());
        summary.setFacility(source.getEffectiveFacilityName());
        FacilityType facilityType = source.getFacilityType();
        summary.setFacilityType(facilityType == null ? "" : facilityType.getName());
        summary.setProgram(source.getAssessmentTool().getAssessmentToolMode().getName());
        summary.setSystemId(source.getUuidString());
        return summary;
    }
}
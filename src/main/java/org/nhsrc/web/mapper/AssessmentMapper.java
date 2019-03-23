package org.nhsrc.web.mapper;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.web.contract.ext.AssessmentSummaryResponse;

public class AssessmentMapper {
    public static AssessmentSummaryResponse map(AssessmentSummaryResponse summary, FacilityAssessment source, AssessmentTool assessmentTool) {
        summary.setAssessmentEndDate(source.getEndDate());
        summary.setAssessmentSeries(source.getSeriesName());
        summary.setAssessmentStartDate(source.getStartDate());
        summary.setAssessmentTool(assessmentTool.getName());
        summary.setAssessmentType(source.getAssessmentType().getName());
        summary.setDistrict(source.getDistrict().getName());
        summary.setState(source.getState().getName());
        summary.setFacility(source.getEffectiveFacilityName());
        summary.setFacilityType(source.getFacilityType().getName());
        summary.setProgram(source.getAssessmentTool().getAssessmentToolMode().getName());
        summary.setSystemId(source.getUuidString());
        return summary;
    }
}
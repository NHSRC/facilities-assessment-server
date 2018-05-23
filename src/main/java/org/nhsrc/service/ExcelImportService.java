package org.nhsrc.service;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.referenceDataImport.AssessmentChecklistData;
import org.nhsrc.referenceDataImport.ExcelImporter;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.web.contract.FacilityAssessmentExcelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ExcelImportService {
    private AssessmentToolRepository assessmentToolRepository;

    @Autowired
    public ExcelImportService(AssessmentToolRepository assessmentToolRepository) {
        this.assessmentToolRepository = assessmentToolRepository;
    }

    public AssessmentChecklistData parseAssessment(InputStream inputStream, FacilityAssessmentDTO facilityAssessmentDTO, FacilityAssessment facilityAssessment) throws Exception {
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        ExcelImporter excelImporter = new ExcelImporter(assessmentChecklistData);
        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(facilityAssessmentDTO.getAssessmentTool());
        excelImporter.importFile(inputStream, assessmentTool, 0, 1, true, facilityAssessment);
        return assessmentChecklistData;
    }
}
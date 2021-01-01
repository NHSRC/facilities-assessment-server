package org.nhsrc.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.repository.FacilityAssessmentRepository;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AssessmentMatchingServiceTest {
    @Mock
    private FacilityAssessmentRepository facilityAssessmentRepository;
    private String seriesName;
    private AssessmentTool assessmentTool;
    private UUID facilityAssessmentUUID;
    private Facility facility;
    private AssessmentMatchingService assessmentMatchingService;

    @Before
    public void before() {
        initMocks(this);
        facilityAssessmentUUID = UUID.randomUUID();
        facility = createFacility(1);
        assessmentTool = createAssessmentTool(1);
        seriesName = "11";
        assessmentMatchingService = new AssessmentMatchingService(facilityAssessmentRepository);
    }

    @Test
    public void findExistingAssessmentWhenSameSeriesExistsButIsSubmittedForFirstTime() {
        when(facilityAssessmentRepository.findByUuid(facilityAssessmentUUID)).thenReturn(null);
        when(facilityAssessmentRepository.findByFacilityAndAssessmentToolAndSeriesName(facility, assessmentTool, seriesName)).thenReturn(new FacilityAssessment());
        FacilityAssessment existingAssessment = assessmentMatchingService.findExistingAssessment(seriesName, facilityAssessmentUUID, facility, null, assessmentTool);
        Assert.assertNotEquals(null, existingAssessment);
    }

    @Test
    public void doNotFindExistingAssessmentWhenSameSeriesIsProvidedButIsSubmittedForFirstTime() {
        when(facilityAssessmentRepository.findByUuid(facilityAssessmentUUID)).thenReturn(null);
        when(facilityAssessmentRepository.findByFacilityAndAssessmentToolAndSeriesName(facility, assessmentTool, seriesName)).thenReturn(null);
        FacilityAssessment existingAssessment = assessmentMatchingService.findExistingAssessment(seriesName, facilityAssessmentUUID, facility, null, assessmentTool);
        Assert.assertEquals(null, existingAssessment);
    }

    @Test
    public void findExistingAssessmentByUUID() {
        when(facilityAssessmentRepository.findByUuid(facilityAssessmentUUID)).thenReturn(new FacilityAssessment());
        FacilityAssessment existingAssessment = assessmentMatchingService.findExistingAssessment(seriesName, facilityAssessmentUUID, facility, null, assessmentTool);
        Assert.assertNotEquals(null, existingAssessment);
    }

    @Test
    public void noAssessmentExists() {
        when(facilityAssessmentRepository.findByUuid(facilityAssessmentUUID)).thenReturn(null);
        when(facilityAssessmentRepository.findByFacilityAndAssessmentToolAndSeriesName(facility, assessmentTool, seriesName)).thenReturn(null);
        FacilityAssessment existingAssessment = assessmentMatchingService.findExistingAssessment(seriesName, facilityAssessmentUUID, facility, null, assessmentTool);
        Assert.assertEquals(null, existingAssessment);
    }

    @Test
    public void doNotFindExistingAssessmentWhenSameSeriesIsProvidedButFacilityIsNullAndFacilityNameIsDifferent() {
        when(facilityAssessmentRepository.findByUuid(facilityAssessmentUUID)).thenReturn(null);
        when(facilityAssessmentRepository.findByFacilityAndAssessmentToolAndSeriesName(null, assessmentTool, seriesName)).thenReturn(new FacilityAssessment());
        when(facilityAssessmentRepository.findByFacilityNameAndAssessmentToolAndSeriesName("test", assessmentTool, seriesName)).thenReturn(null);
        FacilityAssessment existingAssessment = assessmentMatchingService.findExistingAssessment(seriesName, facilityAssessmentUUID, null, "test2", assessmentTool);
        Assert.assertEquals(null, existingAssessment);
    }

    @Test
    public void findExistingAssessmentWhenSameSeriesIsProvidedButFacilityIsNullAndFacilityNameIsSame() {
        when(facilityAssessmentRepository.findByUuid(facilityAssessmentUUID)).thenReturn(null);
        when(facilityAssessmentRepository.findByFacilityAndAssessmentToolAndSeriesName(null, assessmentTool, seriesName)).thenReturn(null);
        when(facilityAssessmentRepository.findByFacilityNameAndAssessmentToolAndSeriesName("test", assessmentTool, seriesName)).thenReturn(new FacilityAssessment());
        FacilityAssessment existingAssessment = assessmentMatchingService.findExistingAssessment(seriesName, facilityAssessmentUUID, null, "test", assessmentTool);
        Assert.assertNotEquals(null, existingAssessment);
    }

    private AssessmentTool createAssessmentTool(int id) {
        AssessmentTool assessmentTool = new AssessmentTool();
        assessmentTool.setId(id);
        return assessmentTool;
    }

    private Facility createFacility(int id) {
        Facility facility = new Facility();
        facility.setId(id);
        return facility;
    }
}
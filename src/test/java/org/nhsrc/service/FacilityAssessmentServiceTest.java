package org.nhsrc.service;

import org.junit.Before;
import org.junit.Test;
import org.nhsrc.builder.FacilityAssessmentDTOBuilder;
import org.nhsrc.common.AbstractWebIntegrationTest;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class FacilityAssessmentServiceTest extends AbstractWebIntegrationTest {

    @Autowired
    private FacilityAssessmentService facilityAssessmentService;

    @Autowired
    private FacilityAssessmentRepository facilityAssessmentRepository;

    private FacilityAssessmentDTOBuilder facilityAssessmentDTOBuilder;

    @Before
    public void setUp() throws Exception {
        facilityAssessmentDTOBuilder = new FacilityAssessmentDTOBuilder();
    }

    @Test
    public void negativeMatchTest() throws Exception {
        String uuid = "9dfbd093-0614-4038-88ab-fb1a154add4f";
        FacilityAssessmentDTO facilityAssessmentDTO = facilityAssessmentDTOBuilder.withUUID(uuid)
                .asDistrictHospital()
                .build();
        FacilityAssessment savedFacilityAssessment = facilityAssessmentService.save(facilityAssessmentDTO);
        assertEquals(uuid, savedFacilityAssessment.getUuid().toString());
    }

    @Test
    public void positiveMatchTestPlus1Day() throws Exception {
        long prevCount = facilityAssessmentRepository.count();
        String uuid1 = "9144c1a3-486e-42a2-9df9-b4d4ab6349bb";
        String uuid2 = "78da25da-fbd4-41f1-bf76-bd8d8ea3f858";
        FacilityAssessmentDTO facilityAssessmentDTO1 = facilityAssessmentDTOBuilder
                .withUUID(uuid1)
                .asDistrictHospital()
                .build();
        FacilityAssessment savedAssessment1 = facilityAssessmentService.save(facilityAssessmentDTO1);
        Calendar calendar = Calendar.getInstance();
        Date startDate = savedAssessment1.getStartDate();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, 1);
        Date newStartDate = calendar.getTime();
        FacilityAssessmentDTO facilityAssessmentDTO2 = facilityAssessmentDTOBuilder
                .withUUID(uuid2)
                .asDistrictHospital()
                .withStartDate(newStartDate)
                .build();
        FacilityAssessment savedAssessment2 = facilityAssessmentService.save(facilityAssessmentDTO2);
        assertEquals(savedAssessment1.getUuid(), savedAssessment2.getUuid());
        assertEquals(uuid1, savedAssessment2.getUuid().toString());
        long count = facilityAssessmentRepository.count();
        assertEquals(1L, (count - prevCount));
    }

    @Test
    public void positiveMatchTestImmediateCreation() throws Exception {
        long prevCount = facilityAssessmentRepository.count();
        String uuid1 = "cadeb162-dcaa-4891-b9e8-0b6073ae9666";
        String uuid2 = "5e7b0828-9335-4a16-877f-5eba875ca477";
        FacilityAssessmentDTO facilityAssessmentDTO1 = facilityAssessmentDTOBuilder
                .withUUID(uuid1)
                .asDistrictHospital()
                .build();
        FacilityAssessment savedAssessment1 = facilityAssessmentService.save(facilityAssessmentDTO1);
        FacilityAssessmentDTO facilityAssessmentDTO2 = facilityAssessmentDTOBuilder
                .withUUID(uuid2)
                .asDistrictHospital()
                .withStartDate(new Date())
                .build();
        FacilityAssessment savedAssessment2 = facilityAssessmentService.save(facilityAssessmentDTO2);
        assertEquals(savedAssessment1.getUuid(), savedAssessment2.getUuid());
        assertEquals(uuid1, savedAssessment2.getUuid().toString());
        long count = facilityAssessmentRepository.count();
        assertEquals(1L, (count - prevCount));
    }
}
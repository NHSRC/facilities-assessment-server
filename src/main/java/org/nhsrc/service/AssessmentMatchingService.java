package org.nhsrc.service;

import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class AssessmentMatchingService {
    private final FacilityAssessmentRepository facilityAssessmentRepository;

    @Autowired
    public AssessmentMatchingService(FacilityAssessmentRepository facilityAssessmentRepository) {
        this.facilityAssessmentRepository = facilityAssessmentRepository;
    }

    @Value("${fa.submission.window}")
    private int submissionWindow;

    public FacilityAssessment findMatching(FacilityAssessment facilityAssessment) {
        Calendar calendar = Calendar.getInstance();
        Date startDate = facilityAssessment.getStartDate();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, submissionWindow);
        Date dateBefore = calendar.getTime();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, -submissionWindow);
        Date dateAfter = calendar.getTime();
        FacilityAssessment matchingAssessment =
                this.facilityAssessmentRepository
                        .findByFacilityAndAssessmentToolAndStartDateBeforeAndStartDateAfter(
                                facilityAssessment.getFacility(),
                                facilityAssessment.getAssessmentTool(),
                                dateBefore,
                                dateAfter);
        return matchingAssessment == null ? facilityAssessment : matchingAssessment;
    }
}

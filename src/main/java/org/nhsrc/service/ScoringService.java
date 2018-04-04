package org.nhsrc.service;

import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.scores.ScoringProcessDetail;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.repository.scores.ScoringProcessDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.nhsrc.service.ScoringSQLs.*;

@Service
public class ScoringService {
    private ScoringProcessDetailRepository scoringProcessDetailRepository;
    private FacilityAssessmentRepository facilityAssessmentRepository;
    private EntityManager entityManager;

    @Autowired
    public ScoringService(ScoringProcessDetailRepository scoringProcessDetailRepository, FacilityAssessmentRepository facilityAssessmentRepository, EntityManager entityManager) {
        this.scoringProcessDetailRepository = scoringProcessDetailRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
        this.entityManager = entityManager;
    }

    public void scoreAssessments() {
        ScoringProcessDetail scoringProcessDetail = scoringProcessDetailRepository.findByUuid(ScoringProcessDetail.Fixed_UUID);
        List<FacilityAssessment> unscoredAssessments = facilityAssessmentRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAsc(scoringProcessDetail.getSafeLastScoredUntilTime());
        unscoredAssessments.forEach(facilityAssessment -> {
            scoreAssessment(facilityAssessment, scoringProcessDetail);
        });
    }

    @Transactional
    public void scoreAssessment(FacilityAssessment facilityAssessment, ScoringProcessDetail scoringProcessDetail) {
        deleteScore(facilityAssessment.getId(), Delete_Checklist_Scores);
        deleteScore(facilityAssessment.getId(), Delete_Standard_Scores);
        deleteScore(facilityAssessment.getId(), Delete_AreaOfConcern_Scores);

        createScore(facilityAssessment.getId(), Create_Checklist_Scores);
        createScore(facilityAssessment.getId(), Create_Standard_Scores);
        createScore(facilityAssessment.getId(), Create_AreaOfConcern_Scores);

        scoringProcessDetail.setLastScoredUntil(facilityAssessment.getLastModifiedDate());
        scoringProcessDetailRepository.save(scoringProcessDetail);
    }

    private void createScore(int assessmentId, String sql) {
        entityManager.createNativeQuery(sql).setParameter(AssessmentId_Param, assessmentId).executeUpdate();
    }

    private void deleteScore(int assessmentId, String query) {
        entityManager.createNativeQuery(query).setParameter(AssessmentId_Param, assessmentId).executeUpdate();
    }
}
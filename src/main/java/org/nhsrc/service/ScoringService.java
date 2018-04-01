package org.nhsrc.service;

import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.scores.ScoringProcessDetail;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.repository.scores.ScoringProcessDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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
        List<FacilityAssessment> unscoredAssessments = facilityAssessmentRepository.findByLastModifiedDateGreaterThan(scoringProcessDetail.getSafeLastScoredUntilTime());
        unscoredAssessments.forEach(facilityAssessment -> {
            scoreAssessment(facilityAssessment.getId());
        });
    }

    @Transactional
    public void scoreAssessment(int assessmentId) {
        deleteScore(assessmentId, Delete_Checklist_Scores);
        deleteScore(assessmentId, Delete_Standard_Scores);
        deleteScore(assessmentId, Delete_AreaOfConcern_Scores);

        createScore(assessmentId, Create_Checklist_Scores);
        createScore(assessmentId, Create_Standard_Scores);
        createScore(assessmentId, Create_AreaOfConcern_Scores);
    }

    private void createScore(int assessmentId, String create_checklist_scores) {
        entityManager.createNativeQuery(create_checklist_scores).setParameter(FacilityId_Param, assessmentId).executeUpdate();
    }

    private void deleteScore(int assessmentId, String query) {
        entityManager.createNativeQuery(query).setParameter(FacilityId_Param, assessmentId).executeUpdate();
    }
}
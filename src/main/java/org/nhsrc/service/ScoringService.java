package org.nhsrc.service;

import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.scores.ScoringProcessDetail;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.repository.scores.ScoringProcessDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;

import static org.nhsrc.service.ScoringSQLs.*;

@Service
public class ScoringService {
    private ScoringProcessDetailRepository scoringProcessDetailRepository;
    private FacilityAssessmentRepository facilityAssessmentRepository;
    private EntityManagerFactory entityManagerFactory;
    private static Logger logger = LoggerFactory.getLogger(ScoringService.class);

    @Autowired
    public ScoringService(ScoringProcessDetailRepository scoringProcessDetailRepository, FacilityAssessmentRepository facilityAssessmentRepository, EntityManagerFactory entityManagerFactory) {
        this.scoringProcessDetailRepository = scoringProcessDetailRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    public void scoreAssessments() {
        ScoringProcessDetail scoringProcessDetail = scoringProcessDetailRepository.findByUuid(ScoringProcessDetail.Fixed_UUID);
        Date safeLastScoredUntilTime = scoringProcessDetail.getSafeLastScoredUntilTime();
        logger.info(String.format("Loading assessments done after: %s", safeLastScoredUntilTime.toString()));
        List<FacilityAssessment> unscoredAssessments = facilityAssessmentRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAsc(safeLastScoredUntilTime);
        logger.info(String.format("Found %d assessments, to be scored", unscoredAssessments.size()));
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        unscoredAssessments.forEach(facilityAssessment -> {
            scoreAssessment(facilityAssessment, scoringProcessDetail, entityManager);
        });
    }

    private void scoreAssessment(FacilityAssessment facilityAssessment, ScoringProcessDetail scoringProcessDetail, EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            deleteScore(facilityAssessment.getId(), Delete_Checklist_Scores, entityManager);
            deleteScore(facilityAssessment.getId(), Delete_Checklist_Only_Scores, entityManager);
            deleteScore(facilityAssessment.getId(), Delete_Standard_Scores, entityManager);
            deleteScore(facilityAssessment.getId(), Delete_AreaOfConcern_Scores, entityManager);
            deleteScore(facilityAssessment.getId(), Delete_Assessment_Scores, entityManager);

            createScore(facilityAssessment.getId(), Create_Checklist_Scores, entityManager);
            createScore(facilityAssessment.getId(), Create_Checklist_Only_Scores, entityManager);
            createScore(facilityAssessment.getId(), Create_Standard_Scores, entityManager);
            createScore(facilityAssessment.getId(), Create_AreaOfConcern_Scores, entityManager);
            createScore(facilityAssessment.getId(), Create_Assessment_Scores, entityManager);

            scoringProcessDetail.setLastScoredUntil(facilityAssessment.getLastModifiedDate());
            scoringProcessDetailRepository.save(scoringProcessDetail);
            transaction.commit();
        } catch (Exception e) {
            logger.error(String.format("Couldn't score assessment: %s", facilityAssessment.getUuid()), e);
            transaction.rollback();
            throw e;
        }
    }

    private void createScore(int assessmentId, String sql, EntityManager entityManager) {
        entityManager.createNativeQuery(sql).setParameter(AssessmentId_Param, assessmentId).executeUpdate();
    }

    private void deleteScore(int assessmentId, String query, EntityManager entityManager) {
        entityManager.createNativeQuery(query).setParameter(AssessmentId_Param, assessmentId).executeUpdate();
    }
}
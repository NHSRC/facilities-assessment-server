package org.nhsrc.web;

import org.nhsrc.data.AreaOfConcernTotal;
import org.nhsrc.data.ChecklistTotal;
import org.nhsrc.data.StandardTotal;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.dto.AreaOfConcernProgressDTO;
import org.nhsrc.dto.ChecklistProgressDTO;
import org.nhsrc.dto.FacilityAssessmentProgressDTO;
import org.nhsrc.dto.StandardProgressDTO;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/facilityAssessmentProgress/")
public class FacilityAssessmentProgressController {
    private FacilityAssessmentRepository facilityAssessmentRepository;
    private EntityManager entityManager;

    private static final String checklistScoreCountInAssessment = "SELECT cl.uuid AS uuid, count(cl.id) AS completed, 0 AS total FROM checkpoint_score cps, facility_assessment fa, checklist cl WHERE cps.facility_assessment_id = fa.id AND fa.id = :id AND cps.checklist_id = cl.id GROUP BY cl.id";
    private static final String totalChecklistsCountInAssessmentTool = "SELECT cl.uuid AS uuid, count(cl.id) AS total FROM checkpoint cp, assessment_tool at, checklist cl WHERE cp.checklist_id = cl.id AND cl.assessment_tool_id = at.id AND at.id = :id GROUP BY cl.id";

    private static final String aocScoreCountInAssessment = "SELECT aoc.uuid AS uuid, cl.uuid AS checklistUUID, count(aoc.id) AS completed, 0 AS total FROM checkpoint cp, checkpoint_score cps, facility_assessment fa, standard s, measurable_element me, area_of_concern aoc, checklist cl WHERE cps.checkpoint_id = cp.id AND cps.facility_assessment_id = fa.id AND cp.measurable_element_id = me.id AND me.standard_id = s.id AND s.area_of_concern_id = aoc.id AND cp.checklist_id = cl.id AND fa.id = :id GROUP BY (cl.id, aoc.id)";
    private static final String totalAOCsCountInAssessmentTool = "SELECT aoc.uuid AS uuid, cl.uuid AS checklistUUID, count(aoc.id) AS total FROM checkpoint cp, area_of_concern aoc, standard s, measurable_element me, assessment_tool at, checklist cl WHERE cp.measurable_element_id = me.id AND me.standard_id = s.id AND cp.checklist_id = cl.id AND cl.assessment_tool_id = at.id AND aoc.id = s.area_of_concern_id AND at.id = :id GROUP BY (cl.id, aoc.id)";


    private static final String standardsScoreCountInAssessment = "SELECT s.uuid as uuid, cl.uuid as checklistUUID, aoc.uuid as aocUUID, count(s.uuid) as completed, 0 as total FROM checkpoint cp, checkpoint_score cps, facility_assessment fa, area_of_concern aoc, standard s, measurable_element me, checklist cl WHERE cps.checkpoint_id = cp.id AND cps.facility_assessment_id = fa.id AND cp.measurable_element_id = me.id AND me.standard_id = s.id AND cp.checklist_id = cl.id AND aoc.id = s.area_of_concern_id AND fa.id = :id GROUP BY (cl.id, aoc.id, s.uuid)";
    private static final String totalStandardsInAssessmentTool = "select s.uuid as uuid, cl.uuid as checklistUUID, aoc.uuid as aocUUID, count(s.id) as total from checkpoint cp, standard s, measurable_element me, assessment_tool at, checklist cl, area_of_concern aoc where cp.measurable_element_id = me.id and me.standard_id = s.id and cp.checklist_id = cl.id and cl.assessment_tool_id = at.id and s.area_of_concern_id = aoc.id AND at.id = :id GROUP BY (cl.id, aoc.id, s.uuid)";

    @Autowired
    public FacilityAssessmentProgressController(FacilityAssessmentRepository facilityAssessmentRepository, EntityManager entityManager) {
        this.facilityAssessmentRepository = facilityAssessmentRepository;
        this.entityManager = entityManager;
    }

    @RequestMapping(value = "search/lastModified", method = RequestMethod.GET)
    public ResponseEntity<List<FacilityAssessmentProgressDTO>> getFacilityAssessmentProgress(@RequestParam String lastModifiedDate) throws ParseException {
        Date result = DateUtils.ISO_8601_DATE_FORMAT.parse(lastModifiedDate);
        List<FacilityAssessment> facilityAssessments = facilityAssessmentRepository.findByLastModifiedDateGreaterThan(result);
        List<FacilityAssessmentProgressDTO> facilityAssessmentsProgress = new ArrayList<>();
        facilityAssessments.forEach(facilityAssessment -> {
            FacilityAssessmentProgressDTO assessmentProgress = new FacilityAssessmentProgressDTO();
            assessmentProgress.setUuid(facilityAssessment.getUuid().toString());

            //CHECKLIST
            Query totalQuery = entityManager.createNativeQuery(totalChecklistsCountInAssessmentTool, ChecklistTotal.class);
            totalQuery.setParameter("id", facilityAssessment.getAssessmentTool().getId());
            List totalResultList = totalQuery.getResultList();

            Query progressQuery = entityManager.createNativeQuery(checklistScoreCountInAssessment, ChecklistProgressDTO.class);
            progressQuery.setParameter("id", facilityAssessment.getId());
            final List checklistProgressList = progressQuery.getResultList();

            totalResultList.forEach(checklistEntityTotal -> {
                ChecklistProgressDTO entityProgressDTO = new ChecklistProgressDTO(((ChecklistTotal) checklistEntityTotal).getUuid(), 0, 0);
                int index = checklistProgressList.indexOf(entityProgressDTO);

                ChecklistProgressDTO checklistProgressDTO;
                if (index > -1) {
                    checklistProgressDTO = (ChecklistProgressDTO) checklistProgressList.get(index);
                } else {
                    checklistProgressDTO = entityProgressDTO;
                }
                checklistProgressDTO.setTotal(((ChecklistTotal) checklistEntityTotal).getTotal());
                assessmentProgress.addChecklistProgress(checklistProgressDTO);
            });

            //AREA OF CONCERN
            totalQuery = entityManager.createNativeQuery(totalAOCsCountInAssessmentTool, AreaOfConcernTotal.class);
            totalQuery.setParameter("id", facilityAssessment.getAssessmentTool().getId());
            totalResultList = totalQuery.getResultList();

            progressQuery = entityManager.createNativeQuery(aocScoreCountInAssessment, AreaOfConcernProgressDTO.class);
            progressQuery.setParameter("id", facilityAssessment.getId());
            final List aocProgressList = progressQuery.getResultList();

            totalResultList.forEach(totalResultItem -> {
                AreaOfConcernTotal aocEntityTotal = (AreaOfConcernTotal) totalResultItem;
                AreaOfConcernProgressDTO entityProgressDTO = new AreaOfConcernProgressDTO(aocEntityTotal.getUuid(), 0, 0, aocEntityTotal.getChecklistUUID());
                int index = aocProgressList.indexOf(entityProgressDTO);

                AreaOfConcernProgressDTO aocProgressDTO;
                if (index > -1) {
                    aocProgressDTO = (AreaOfConcernProgressDTO) aocProgressList.get(index);
                } else {
                    aocProgressDTO = entityProgressDTO;
                }
                aocProgressDTO.setTotal(((AreaOfConcernTotal) totalResultItem).getTotal());
                assessmentProgress.addAreaOfConcernProgress(aocProgressDTO);
            });

            //STANDARD
            totalQuery = entityManager.createNativeQuery(totalStandardsInAssessmentTool, StandardTotal.class);
            totalQuery.setParameter("id", facilityAssessment.getAssessmentTool().getId());
            totalResultList = totalQuery.getResultList();

            progressQuery = entityManager.createNativeQuery(standardsScoreCountInAssessment, StandardProgressDTO.class);
            progressQuery.setParameter("id", facilityAssessment.getId());
            final List standardProgressList = progressQuery.getResultList();

            totalResultList.forEach(totalResultItem -> {
                StandardTotal standardTotal = (StandardTotal) totalResultItem;
                StandardProgressDTO entityProgressDTO = new StandardProgressDTO(standardTotal.getUuid(), 0, 0, standardTotal.getChecklistUUID(), standardTotal.getAocUUID());
                int index = standardProgressList.indexOf(entityProgressDTO);

                StandardProgressDTO standardProgressDTO;
                if (index > -1) {
                    standardProgressDTO = (StandardProgressDTO) standardProgressList.get(index);
                } else {
                    standardProgressDTO = entityProgressDTO;
                }
                standardProgressDTO.setTotal(((StandardTotal) totalResultItem).getTotal());
                assessmentProgress.addStandardProgress(standardProgressDTO);
            });

            facilityAssessmentsProgress.add(assessmentProgress);
            System.out.println(String.format("ChecklistProgressCount: %d; AOCProgressCount: %d; StandardProgressCount: %d", assessmentProgress.getChecklistsProgress().size(), assessmentProgress.getAreaOfConcernsProgress().size(), assessmentProgress.getStandardsProgress().size()));
        });
        return ResponseEntity.ok(facilityAssessmentsProgress);
    }
}
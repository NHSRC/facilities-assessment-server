package org.nhsrc.web;

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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/facilityAssessmentProgress/")
public class FacilityAssessmentProgressController {
    private FacilityAssessmentRepository facilityAssessmentRepository;
    private EntityManager entityManager;

    private static final String standardProgressPerAssessment = "SELECT\n" +
            "  s.uuid             uuid,\n" +
            "  aoc.uuid           aocUUID,\n" +
            "  ch.uuid            checklistUUID,\n" +
            "  count(c.id)     AS total,\n" +
            "  sum(CASE WHEN cs.id IS NULL\n" +
            "    THEN 0\n" +
            "      ELSE 1 END) AS completed\n" +
            "FROM checkpoint c\n" +
            "  LEFT OUTER JOIN checkpoint_score cs ON cs.checkpoint_id = c.id\n" +
            "  INNER JOIN measurable_element me ON c.measurable_element_id = me.id\n" +
            "  INNER JOIN standard s ON me.standard_id = s.id\n" +
            "  INNER JOIN area_of_concern aoc ON s.area_of_concern_id = aoc.id\n" +
            "  INNER JOIN checklist ch ON c.checklist_id = ch.id\n" +
            "  LEFT OUTER JOIN facility_assessment fa on cs.facility_assessment_id=fa.id and fa.id=:id \n" +
            "GROUP BY s.id, ch.id, aoc.id";

    private static final String areaOfConcernTotalForAssessmentTool = "SELECT\n" +
            "  ch.uuid              AS checklistUUID,\n" +
            "  aoc.uuid             AS uuid,\n" +
            "  count(distinct s.id) AS total,\n" +
            "  0                    AS completed\n" +
            "FROM checkpoint c\n" +
            "  INNER JOIN measurable_element me ON c.measurable_element_id = me.id\n" +
            "  INNER JOIN standard s ON me.standard_id = s.id\n" +
            "  INNER JOIN area_of_concern aoc ON s.area_of_concern_id = aoc.id\n" +
            "  INNER JOIN assessment_tool at ON s.assessment_tool_id = at.id\n" +
            "  INNER JOIN checklist ch ON ch.id=c.checklist_id\n" +
            "  where at.id=:id and c.id is not null\n" +
            "GROUP BY ch.id, aoc.id";

    private static final String checklistTotalForAssessmentTool = "SELECT\n" +
            "  ch.uuid              AS checklistUUID,\n" +
            "  count(distinct aoc.id) AS total,\n" +
            "  0                    AS completed\n" +
            "FROM checkpoint c\n" +
            "  INNER JOIN measurable_element me ON c.measurable_element_id = me.id\n" +
            "  INNER JOIN standard s ON me.standard_id = s.id\n" +
            "  INNER JOIN area_of_concern aoc ON s.area_of_concern_id = aoc.id\n" +
            "  INNER JOIN assessment_tool at ON s.assessment_tool_id = at.id\n" +
            "  INNER JOIN checklist ch ON ch.id=c.checklist_id\n" +
            "where at.id=:id and c.id is not null\n" +
            "GROUP BY ch.id";

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

            //Standard
            List<StandardProgressDTO> standardResultList = new ArrayList<>();
            standardResultList.addAll(getStandardProgressDTO(facilityAssessment));
            assessmentProgress.setStandardsProgress(standardResultList);

            //Area Of Concern
            List<AreaOfConcernProgressDTO> areasOfConcernProgressDTO = new ArrayList<>();
            areasOfConcernProgressDTO.addAll(getAreaOfConcernProgressDTO(facilityAssessment, standardResultList));
            assessmentProgress.setAreaOfConcernsProgress(areasOfConcernProgressDTO);

            //Checklist
            List<ChecklistProgressDTO> checklistProgressDTOs = new ArrayList<>();
            checklistProgressDTOs.addAll(getChecklistProgress(facilityAssessment, areasOfConcernProgressDTO));
            assessmentProgress.setChecklistsProgress(checklistProgressDTOs);

            facilityAssessmentsProgress.add(assessmentProgress);
        });
        return ResponseEntity.ok(facilityAssessmentsProgress);
    }

    private List<StandardProgressDTO> getStandardProgressDTO(FacilityAssessment facilityAssessment) {
        Query standardProgressForAssessment = entityManager.createNativeQuery(standardProgressPerAssessment, StandardProgressDTO.class);
        standardProgressForAssessment.setParameter("id", facilityAssessment.getId());
        return (List<StandardProgressDTO>) standardProgressForAssessment.getResultList();
    }

    private List<AreaOfConcernProgressDTO> getAreaOfConcernProgressDTO(FacilityAssessment facilityAssessment, List<StandardProgressDTO> standardsProgressDTO) {
        Query areaOfConcernProgressForAssessment = entityManager.createNativeQuery(areaOfConcernTotalForAssessmentTool, AreaOfConcernProgressDTO.class);
        areaOfConcernProgressForAssessment.setParameter("id", facilityAssessment.getAssessmentTool().getId());
        List<AreaOfConcernProgressDTO> areasOfConcernProgress = (List<AreaOfConcernProgressDTO>) areaOfConcernProgressForAssessment.getResultList();

        Map<String, List<StandardProgressDTO>> standardProgressByAOC = standardsProgressDTO.stream().collect(Collectors.groupingBy(StandardProgressDTO::getAocUUID));

        return areasOfConcernProgress.parallelStream().peek(areaOfConcernProgressDTO -> areaOfConcernProgressDTO.setCompleted((int) standardProgressByAOC.get(areaOfConcernProgressDTO.getUuid()).stream().filter(standardProgressDTO -> standardProgressDTO.getTotal() == standardProgressDTO.getCompleted()).count())).collect(Collectors.toList());
    }

    private List<ChecklistProgressDTO> getChecklistProgress(FacilityAssessment facilityAssessment, List<AreaOfConcernProgressDTO> areasOfConcernProgressDTO) {
        Query checklistProgressForAssessmentTool = entityManager.createNativeQuery(checklistTotalForAssessmentTool, ChecklistProgressDTO.class);
        checklistProgressForAssessmentTool.setParameter("id", facilityAssessment.getAssessmentTool().getId());
        List<ChecklistProgressDTO> checklistProgressDTOS = (List<ChecklistProgressDTO>) checklistProgressForAssessmentTool.getResultList();

        Map<String, List<AreaOfConcernProgressDTO>> aocProgressByChecklist = areasOfConcernProgressDTO.stream().collect(Collectors.groupingBy(AreaOfConcernProgressDTO::getChecklistUUID));

        return checklistProgressDTOS.parallelStream()
                .peek(checklistProgressDTO -> checklistProgressDTO.setCompleted((int) aocProgressByChecklist.get(checklistProgressDTO.getUuid()).stream().filter(aocProgressDTO -> aocProgressDTO.getTotal() == aocProgressDTO.getCompleted()).count())).collect(Collectors.toList());
    }
}
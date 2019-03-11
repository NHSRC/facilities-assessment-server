package org.nhsrc.service;

import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.dto.AreaOfConcernProgressDTO;
import org.nhsrc.dto.ChecklistProgressDTO;
import org.nhsrc.dto.FacilityAssessmentProgressDTO;
import org.nhsrc.dto.StandardProgressDTO;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AssessmentProgressService {
    private EntityManager entityManager;
    private FacilityAssessmentRepository facilityAssessmentRepository;

    private static final String standardProgressPerAssessment =
            "SELECT * from ( " +
            " SELECT" +
            "  fa.id AS facility_assessment_id," +
            "  std.uuid           uuid," +
            "  aoc.uuid           aocUUID," +
            "  ch.uuid            checklistUUID," +
            "  count(c.id)     AS total," +
            "  sum(CASE WHEN cs.checkpoint_id IS NULL" +
            "    THEN 0" +
            "      ELSE 1 END) AS completed" +
            " FROM checkpoint c" +
            "  INNER JOIN measurable_element me ON c.measurable_element_id = me.id" +
            "  INNER JOIN standard std ON me.standard_id = std.id" +
            "  INNER JOIN area_of_concern aoc ON std.area_of_concern_id = aoc.id" +
            "  INNER JOIN checklist ch ON c.checklist_id = ch.id AND ch.assessment_tool_id = :atid AND (ch.state_id=:stid OR ch.state_id is NULL)" +
            "  LEFT OUTER JOIN (SELECT DISTINCT checkpoint_id" +
            "                   FROM checkpoint_score" +
            "                   WHERE checkpoint_score.facility_assessment_id = :faid) AS cs ON c.id = cs.checkpoint_id" +
            "  LEFT OUTER JOIN facility_assessment fa ON fa.id = :faid" +
            " GROUP BY std.id, ch.id, aoc.id, fa.id) foo" +
                    " where completed != 0";

    private static final String areaOfConcernTotalForAssessmentTool = "SELECT\n" +
            "  ch.id              AS checklist_id,\n" +
            "  ch.uuid              AS checklistUUID,\n" +
            "  aoc.uuid             AS uuid,\n" +
            "  aoc.id             AS id,\n" +
            "  count(DISTINCT s.id) AS total,\n" +
            "  0                    AS completed\n" +
            "FROM checkpoint c\n" +
            "  INNER JOIN measurable_element me ON c.measurable_element_id = me.id\n" +
            "  INNER JOIN standard s ON me.standard_id = s.id\n" +
            "  INNER JOIN area_of_concern aoc ON s.area_of_concern_id = aoc.id\n" +
            "  INNER JOIN checklist_area_of_concern caoc ON caoc.area_of_concern_id = aoc.id\n" +
            "  INNER JOIN checklist ch ON ch.id=c.checklist_id AND (ch.state_id=:stid OR ch.state_id is NULL) AND ch.id = caoc.checklist_id\n" +
            "  INNER JOIN assessment_tool at ON ch.assessment_tool_id = at.id\n" +
            "  WHERE at.id=:id AND c.id IS NOT NULL\n" +
            "GROUP BY ch.id, aoc.id order by ch.id, aoc.id";

    private static final String checklistTotalForAssessmentTool = "SELECT\n" +
            "  ch.id        AS id,\n" +
            "  ch.uuid              AS uuid,\n" +
            "  count(DISTINCT aoc.id) AS total,\n" +
            "  0                    AS completed\n" +
            "FROM checkpoint c\n" +
            "  INNER JOIN measurable_element me ON c.measurable_element_id = me.id\n" +
            "  INNER JOIN standard s ON me.standard_id = s.id\n" +
            "  INNER JOIN area_of_concern aoc ON s.area_of_concern_id = aoc.id\n" +
            "  INNER JOIN checklist_area_of_concern caoc ON caoc.area_of_concern_id = aoc.id\n" +
            "  INNER JOIN checklist ch ON ch.id=c.checklist_id AND (ch.state_id=:stid OR ch.state_id is NULL) AND ch.id = caoc.checklist_id\n" +
            "  INNER JOIN assessment_tool at ON ch.assessment_tool_id = at.id\n" +
            "WHERE at.id=:id AND c.id IS NOT NULL\n" +
            "GROUP BY ch.id";

    @Autowired
    public AssessmentProgressService(EntityManager entityManager, FacilityAssessmentRepository facilityAssessmentRepository) {
        this.entityManager = entityManager;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
    }

    public List<FacilityAssessmentProgressDTO> getFacilityAssessmentProgress(@RequestParam String lastModifiedDate) throws ParseException {
        Date result = DateUtils.ISO_8601_DATE_FORMAT.parse(lastModifiedDate);
        List<FacilityAssessment> facilityAssessments = facilityAssessmentRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAsc(result);
        return getProgressFor(facilityAssessments);
    }

    private List<StandardProgressDTO> getStandardProgressDTO(FacilityAssessment facilityAssessment) {
        Query standardProgressForAssessment = entityManager.createNativeQuery(standardProgressPerAssessment, StandardProgressDTO.class);
        standardProgressForAssessment.setParameter("faid", facilityAssessment.getId());
        standardProgressForAssessment.setParameter("stid", facilityAssessment.getState().getId());
        standardProgressForAssessment.setParameter("atid", facilityAssessment.getAssessmentTool().getId());
        return (List<StandardProgressDTO>) standardProgressForAssessment.getResultList();
    }

    private List<AreaOfConcernProgressDTO> getAreaOfConcernProgressDTO(FacilityAssessment facilityAssessment, List<StandardProgressDTO> standardsProgressDTO) {
        Query areaOfConcernProgressForAssessment = entityManager.createNativeQuery(areaOfConcernTotalForAssessmentTool, AreaOfConcernProgressDTO.class);
        areaOfConcernProgressForAssessment.setParameter("id", facilityAssessment.getAssessmentTool().getId());
        areaOfConcernProgressForAssessment.setParameter("stid", facilityAssessment.getState().getId());
        List<AreaOfConcernProgressDTO> areasOfConcernProgress = (List<AreaOfConcernProgressDTO>) areaOfConcernProgressForAssessment.getResultList();

        Map<String, List<StandardProgressDTO>> standardProgressByAOC = standardsProgressDTO.stream().collect(Collectors.groupingBy(standardProgressDTO -> standardProgressDTO.getAocUUID() + standardProgressDTO.getChecklistUUID()));

        return areasOfConcernProgress.parallelStream().peek(areaOfConcernProgressDTO -> {
            List<StandardProgressDTO> standardProgressDTOS = standardProgressByAOC.get(areaOfConcernProgressDTO.getUuid() + areaOfConcernProgressDTO.getChecklistUUID());
            if (standardProgressDTOS != null) {
                int count = (int) standardProgressDTOS.stream().filter(standardProgressDTO -> standardProgressDTO.getTotal() == standardProgressDTO.getCompleted()).count();
                areaOfConcernProgressDTO.setCompleted(count);
                areaOfConcernProgressDTO.setAnyStandardScored(true);
            }
        }).filter(AreaOfConcernProgressDTO::isAnyStandardScored).collect(Collectors.toList());
    }

    private List<ChecklistProgressDTO> getChecklistProgress(FacilityAssessment facilityAssessment, List<AreaOfConcernProgressDTO> areasOfConcernProgressDTO) {
        Query checklistProgressForAssessmentTool = entityManager.createNativeQuery(checklistTotalForAssessmentTool, ChecklistProgressDTO.class);
        checklistProgressForAssessmentTool.setParameter("id", facilityAssessment.getAssessmentTool().getId());
        checklistProgressForAssessmentTool.setParameter("stid", facilityAssessment.getState().getId());
        List<ChecklistProgressDTO> checklistProgressDTOS = (List<ChecklistProgressDTO>) checklistProgressForAssessmentTool.getResultList();

        Map<String, List<AreaOfConcernProgressDTO>> aocProgressByChecklist = areasOfConcernProgressDTO.stream().collect(Collectors.groupingBy(AreaOfConcernProgressDTO::getChecklistUUID));

        return checklistProgressDTOS.parallelStream()
                .peek(checklistProgressDTO -> {
                    List<AreaOfConcernProgressDTO> areaOfConcernProgressDTOS = aocProgressByChecklist.get(checklistProgressDTO.getUuid());
                    if (areaOfConcernProgressDTOS != null) {
                        int count = (int) areaOfConcernProgressDTOS.stream().filter(aocProgressDTO -> aocProgressDTO.getTotal() == aocProgressDTO.getCompleted()).count();
                        checklistProgressDTO.setCompleted(count);
                        checklistProgressDTO.setAocFilled(true);
                    }
                }).filter(ChecklistProgressDTO::isAocFilled).collect(Collectors.toList());
    }

//    @RequestMapping(value = "facilityAssessmentProgress", method = RequestMethod.GET)
//    Page<FacilityAssessment> getAssessmentsForState(Principal principal, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDate, @RequestParam int size, @RequestParam int page) {
//        User user = userRepository.findByEmail(principal.getName());
//        State state = stateRepository.findOne(user.getUserTypeReferenceId());
//        PageRequest pageable = new PageRequest(page, size);
//        return facilityAssessmentRepository.findByFacilityDistrictStateAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(state, lastModifiedDate, pageable);
//    }

    public List<FacilityAssessmentProgressDTO> getProgressFor(List<FacilityAssessment> facilityAssessments) {
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
            assessmentProgress.setLastModifiedDate(facilityAssessment.getLastModifiedDate());

            facilityAssessmentsProgress.add(assessmentProgress);
        });
        return facilityAssessmentsProgress;
    }
}
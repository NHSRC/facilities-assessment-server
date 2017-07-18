package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.Department;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SQLGenerator {
    public void generateChecklist(AssessmentChecklistData data, File file, boolean assessmentToolExists) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        if (!assessmentToolExists)
            generateAssessmentTool(data, stringBuffer);
        generateDepartment(data, stringBuffer);
        generateChecklist(data, stringBuffer, assessmentToolExists);
        generateAreaOfConcern(data, stringBuffer, assessmentToolExists);
        generateChecklistAreaOfConcernMapping(data, stringBuffer);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(stringBuffer.toString());
        }
    }

    private void generateAssessmentTool(AssessmentChecklistData data, StringBuffer stringBuffer) {
        AssessmentTool assessmentTool = data.getAssessmentTool();
        stringBuffer.append(String.format("insert into assessment_tool (name, mode) values ('%s', '%s');\n", assessmentTool.getName(), assessmentTool.getMode()));
    }

    private void generateChecklistAreaOfConcernMapping(AssessmentChecklistData data, StringBuffer stringBuffer) {
        data.getChecklists().forEach(checklist -> {
            checklist.getAreasOfConcern().forEach(areaOfConcern -> {
                stringBuffer.append(String.format("insert into checklist_area_of_concern (area_of_concern_id, checklist_id) values ((select area_of_concern.id from area_of_concern, assessment_tool where area_of_concern.reference = '%s' and area_of_concern.assessment_tool_id = assessment_tool.id and assessment_tool.name = '%s'), (select id from checklist where name = '%s' and assessment_tool_id = (select id from assessment_tool where name = '%s')));\n", areaOfConcern.getReference(), data.getAssessmentTool().getName(), checklist.getName(), data.getAssessmentTool().getName()));
            });
        });
    }

    private void generateAreaOfConcern(AssessmentChecklistData data, StringBuffer stringBuffer, boolean assessmentToolExists) {
        data.getAreaOfConcerns().forEach(areaOfConcern -> {
            String assessmentToolName = data.getAssessmentTool().getName();
            String existingAOCCheck = assessmentToolExists ? String.format(" where not exists (select area_of_concern.id from area_of_concern, assessment_tool where area_of_concern.reference = '%s' and area_of_concern.assessment_tool_id = assessment_tool.id and assessment_tool.name = '%s')", areaOfConcern.getReference(), assessmentToolName) : "";
            stringBuffer.append(String.format("insert into area_of_concern (name, reference, assessment_tool_id) select '%s', '%s', (select id from assessment_tool where name = '%s')%s;\n", areaOfConcern.getName(), areaOfConcern.getReference(), assessmentToolName, existingAOCCheck));

            areaOfConcern.getStandards().forEach(standard -> {
                String existingStandardCheck = assessmentToolExists ? String.format(" where not exists (select standard.id from assessment_tool, standard where standard.assessment_tool_id = assessment_tool.id and assessment_tool.name = '%s' and standard.reference = '%s')", assessmentToolName, standard.getReference()) : "";
                stringBuffer.append(String.format("insert into standard (name, reference, area_of_concern_id, assessment_tool_id) select '%s', '%s', (select area_of_concern.id from area_of_concern, assessment_tool where area_of_concern.reference = '%s' and area_of_concern.assessment_tool_id = assessment_tool.id and assessment_tool.name = '%s'), (select id from assessment_tool where name = '%s')%s;\n", standard.getName(), standard.getReference(), areaOfConcern.getReference(), assessmentToolName, assessmentToolName, existingStandardCheck));

                standard.getMeasurableElements().forEach(me -> {
                    String existingMECheck = assessmentToolExists ? String.format(" where not exists (select measurable_element.id from assessment_tool, measurable_element where assessment_tool.name = '%s' and measurable_element.reference = '%s' and measurable_element.assessment_tool_id = assessment_tool.id)", assessmentToolName, me.getReference()) : "";
                    stringBuffer.append(String.format("insert into measurable_element (name, reference, standard_id, assessment_tool_id) select '%s', '%s', (select standard.id from standard, assessment_tool where standard.reference = '%s' and standard.assessment_tool_id = assessment_tool.id and assessment_tool.name = '%s'), (select id from assessment_tool where name = '%s')%s;\n", me.getName().replace("'", "''"), me.getReference(), standard.getReference(), assessmentToolName, assessmentToolName, existingMECheck));

                    AtomicInteger sortOrder = new AtomicInteger(0);

                    me.getCheckpoints().forEach(checkpoint -> {
                        String mov = checkpoint.getMeansOfVerification() == null ? "" : checkpoint.getMeansOfVerification().replace("'", "''");
                        stringBuffer.append(String.format("insert into checkpoint (name, means_of_verification, measurable_element_id, checklist_id, is_default, am_observation, am_staff_interview, am_patient_interview, am_record_review, sort_order, is_optional, score_levels) values ('%s', '%s', (select measurable_element.id from measurable_element, assessment_tool where measurable_element.reference = '%s' and measurable_element.assessment_tool_id = assessment_tool.id and assessment_tool.name = '%s'), (select id from checklist where name = '%s' and assessment_tool_id = (select id from assessment_tool where name = '%s')), %b, %b, %b, %b, %b, %d, %b, %d);\n", checkpoint.getName().replace("'", "''"), mov, me.getReference(), assessmentToolName, checkpoint.getChecklist().getName(), data.getAssessmentTool().getName(), checkpoint.getDefault(), checkpoint.getAssessmentMethodObservation(), checkpoint.getAssessmentMethodStaffInterview(), checkpoint.getAssessmentMethodPatientInterview(), checkpoint.getAssessmentMethodRecordReview(), sortOrder.getAndIncrement(), checkpoint.isOptional(), checkpoint.getScoreLevels()));
                    });
                });
            });
        });
    }

    private void generateDepartment(AssessmentChecklistData data, StringBuffer stringBuffer) {
        List<Department> departments = data.getDepartments();
        departments.forEach(department -> {
            stringBuffer.append(String.format("insert into department (name) select '%s' where not exists (select name from department where name = '%s');\n", department.getName(), department.getName()));
        });
    }

    private void generateChecklist(AssessmentChecklistData data, StringBuffer stringBuffer, boolean assessmentToolExists) {
        List<Checklist> checklists = data.getChecklists();
        checklists.forEach(checklist -> {
            String checklistCheck = assessmentToolExists ? String.format(" where not exists (select checklist.id from checklist, assessment_tool where checklist.assessment_tool_id = assessment_tool.id and checklist.name = '%s' and assessment_tool.name = '%s')", checklist.getName(), checklist.getAssessmentTool().getName()) : "";
            stringBuffer.append(String.format("insert into checklist (name, assessment_tool_id, department_id) select '%s', (select id from assessment_tool where name = '%s'), (select id from department where name = '%s')%s;\n", checklist.getName(), checklist.getAssessmentTool().getName(), checklist.getDepartment().getName(), checklistCheck));
        });
    }
}
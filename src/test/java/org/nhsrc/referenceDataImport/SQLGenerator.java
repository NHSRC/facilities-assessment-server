package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.Department;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SQLGenerator {
    public void generate(AssessmentChecklistData data, File file, StringBuffer stringBuffer) throws IOException {
        generateDepartment(data, stringBuffer);
        generateChecklist(data, stringBuffer);
        generateAreaOfConcern(data, stringBuffer);
        generateChecklistAreaOfConcernMapping(data, stringBuffer);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(stringBuffer.toString());
        }
    }

    private void generateChecklistAreaOfConcernMapping(AssessmentChecklistData data, StringBuffer stringBuffer) {
        data.getChecklists().forEach(checklist -> {
            checklist.getAreasOfConcern().forEach(areaOfConcern -> {
                stringBuffer.append(String.format("insert into checklist_area_of_concern (area_of_concern_id, checklist_id) values ((select area_of_concern.id from area_of_concern, assessment_tool where area_of_concern.reference = '%s' and area_of_concern.assessment_tool_id = assessment_tool.id and assessment_tool.name = '%s'), (select id from checklist where name = '%s'));\n", areaOfConcern.getReference(), data.getAssessmentTool().getName(), checklist.getName()));
            });
        });
    }

    private void generateAreaOfConcern(AssessmentChecklistData data, StringBuffer stringBuffer) {
        data.getAreaOfConcerns().forEach(areaOfConcern -> {
            String name = data.getAssessmentTool().getName();
            stringBuffer.append(String.format("insert into area_of_concern (name, reference, assessment_tool_id) values ('%s', '%s', (select id from assessment_tool where name = '%s'));\n", areaOfConcern.getName(), areaOfConcern.getReference(), name));
            areaOfConcern.getStandards().forEach(standard -> {
                stringBuffer.append(String.format("insert into standard (name, reference, area_of_concern_id, assessment_tool_id) values ('%s', '%s', (select area_of_concern.id from area_of_concern, assessment_tool where area_of_concern.reference = '%s' and area_of_concern.assessment_tool_id = assessment_tool.id and assessment_tool.name = '%s'), (select id from assessment_tool where name = '%s'));\n", standard.getName(), standard.getReference(), areaOfConcern.getReference(), name, name));
                standard.getMeasurableElements().forEach(me -> {
                    stringBuffer.append(String.format("insert into measurable_element (name, reference, standard_id, assessment_tool_id) values ('%s', '%s', (select standard.id from standard, assessment_tool where standard.reference = '%s' and standard.assessment_tool_id = assessment_tool.id and assessment_tool.name = '%s'), (select id from assessment_tool where name = '%s'));\n", me.getName().replace("'", "''"), me.getReference(), standard.getReference(), name, name));
                    me.getCheckpoints().forEach(checkpoint -> {
                        String mov = checkpoint.getMeansOfVerification() == null ? "" : checkpoint.getMeansOfVerification().replace("'", "''");
                        stringBuffer.append(String.format("insert into checkpoint (name, means_of_verification, measurable_element_id, checklist_id, is_default, am_observation, am_staff_interview, am_patient_interview, am_record_review) values ('%s', '%s', (select measurable_element.id from measurable_element, assessment_tool where measurable_element.reference = '%s' and measurable_element.assessment_tool_id = assessment_tool.id and assessment_tool.name = '%s'), (select id from checklist where name = '%s'), %b, %b, %b, %b, %b);\n", checkpoint.getName().replace("'", "''"), mov, me.getReference(), name, checkpoint.getChecklist().getName(), checkpoint.getDefault(), checkpoint.getAssessmentMethodObservation(), checkpoint.getAssessmentMethodStaffInterview(), checkpoint.getAssessmentMethodPatientInterview(), checkpoint.getAssessmentMethodRecordReview()));
                    });
                });
            });
        });
    }

    private void generateDepartment(AssessmentChecklistData data, StringBuffer stringBuffer) {
        List<Department> departments = data.getDepartments();
        departments.forEach(department -> {
            stringBuffer.append(String.format("insert into department (name) values ('%s');\n", department.getName()));
        });
    }

    private void generateChecklist(AssessmentChecklistData data, StringBuffer stringBuffer) {
        List<Checklist> checklists = data.getChecklists();
        checklists.forEach(checklist -> {
            stringBuffer.append(String.format("insert into checklist (name, assessment_tool_id, department_id) values ('%s', (select id from assessment_tool where name = '%s'), (select id from department where name = '%s'));\n", checklist.getName(), checklist.getAssessmentTool().getName(), checklist.getDepartment().getName()));
        });
    }
}
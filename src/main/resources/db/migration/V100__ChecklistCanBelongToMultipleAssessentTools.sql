CREATE TABLE assessment_tool_checklist
(
    id                 SERIAL PRIMARY KEY,
    assessment_tool_id INT REFERENCES assessment_tool (id) NOT NULL,
    checklist_id       INT REFERENCES checklist (id)       NOT NULL
);

insert into assessment_tool_checklist (assessment_tool_id, checklist_id) select assessment_tool_id, id from checklist;

alter table checklist drop column assessment_tool_id;
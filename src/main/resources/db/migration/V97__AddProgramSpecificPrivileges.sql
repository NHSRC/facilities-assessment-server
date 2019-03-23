alter table privilege add column assessment_tool_mode_id integer NULL references assessment_tool_mode(id);
alter table privilege add column state_id integer NULL references state(id);
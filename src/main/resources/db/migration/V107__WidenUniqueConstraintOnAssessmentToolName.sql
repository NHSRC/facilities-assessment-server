alter table assessment_tool drop constraint assessment_tool_name_key;
alter table assessment_tool add unique (name, state_id);
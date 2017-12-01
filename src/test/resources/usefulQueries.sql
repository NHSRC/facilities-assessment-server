SELECT count(*) from checklist, department WHERE checklist.name = department.name AND checklist.department_id = department.id;
SELECT count(*) from checklist;
SELECT * from deployment_configuration;
UPDATE deployment_configuration SET recording_mode = true;
UPDATE deployment_configuration SET recording_mode = false;

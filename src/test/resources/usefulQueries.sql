SELECT count(*) from checklist, department WHERE checklist.name = department.name AND checklist.department_id = department.id;
SELECT count(*) from checklist;
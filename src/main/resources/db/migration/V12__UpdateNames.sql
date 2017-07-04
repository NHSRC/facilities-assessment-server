UPDATE assessment_tool set name = 'Community Health Center (CHC)' WHERE name = 'Community Hospital (CH)';
UPDATE assessment_tool set name = 'Urban Primary Health Center (UPHC)' WHERE name = 'Urban Primary Health Center (PHC)';

INSERT INTO facility_type (name) VALUES ('Urban Primary Health Center');
UPDATE facility_type SET name = 'Community Health Center' WHERE name = 'Community Hospital';
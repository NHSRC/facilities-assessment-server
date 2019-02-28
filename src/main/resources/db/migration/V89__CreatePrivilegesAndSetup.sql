create table privilege
(
  id                 SERIAL PRIMARY KEY,
  name               CHARACTER VARYING(255)                                        NOT NULL,
  version            INTEGER                                                       NOT NULL DEFAULT 1,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP        NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP        NOT NULL
);

CREATE TABLE role_privilege
(
  role_id      INT REFERENCES "role" (id)    NOT NULL,
  privilege_id INT REFERENCES privilege (id) NOT NULL
);

insert into privilege (name) values ('Facility_Metadata_Read');
insert into privilege (name) values ('Facility_Metadata_Write');
insert into privilege (name) values ('Facility_Read');
insert into privilege (name) values ('Facility_Write');
insert into privilege (name) values ('Checklist_Read');
insert into privilege (name) values ('Checklist_Write');
insert into privilege (name) values ('Checklist_Metadata_Read');
insert into privilege (name) values ('Checklist_MetadataWrite');
insert into privilege (name) values ('Assessment_Read');
insert into privilege (name) values ('Assessment_Write');
insert into privilege (name) values ('User_Read');
insert into privilege (name) values ('User_Write');
insert into privilege (name) values ('Privilege_Read');
insert into privilege (name) values ('Privilege_Write');
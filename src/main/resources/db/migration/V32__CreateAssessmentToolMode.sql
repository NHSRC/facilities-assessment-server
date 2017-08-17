CREATE TABLE assessment_tool_mode (
  id                 SERIAL PRIMARY KEY,
  name               CHARACTER VARYING(255)                                   NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL DEFAULT 1,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

INSERT INTO assessment_tool_mode (name) VALUES ('dakshata');
INSERT INTO assessment_tool_mode (name) VALUES ('nqas');
INSERT INTO assessment_tool_mode (name) VALUES ('kayakalp');

ALTER TABLE assessment_tool ADD COLUMN assessment_tool_mode_id INT NOT NULL DEFAULT 1;
ALTER TABLE assessment_tool ADD CONSTRAINT assessment_tool_assessment_tool_mode_id_fkey FOREIGN KEY (assessment_tool_mode_id) REFERENCES assessment_tool_mode (id);
update assessment_tool set assessment_tool_mode_id = (select id FROM assessment_tool_mode WHERE assessment_tool.mode = assessment_tool_mode.name);
ALTER TABLE assessment_tool DROP COLUMN mode;
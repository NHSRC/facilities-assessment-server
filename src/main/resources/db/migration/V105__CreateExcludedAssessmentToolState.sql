CREATE TABLE excluded_assessment_tool_state
(
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP             NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP             NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                             NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                              NOT NULL,
  state_id           INT references state (id)                                          NOT NULL,
  assessment_tool_id INT REFERENCES assessment_tool (id)                                NOT NULL
);
CREATE TABLE tag (
  id                 SERIAL PRIMARY KEY,
  name               CHARACTER VARYING(255)                                   NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

CREATE TABLE measurable_element_tag (
  id                 SERIAL PRIMARY KEY,
  tag_id             INTEGER NOT NULL,
  measurable_element_id INTEGER NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

ALTER TABLE ONLY measurable_element_tag
  ADD CONSTRAINT measurable_element_tag_tag FOREIGN KEY (tag_id) REFERENCES tag (id);
ALTER TABLE ONLY measurable_element_tag
  ADD CONSTRAINT measurable_element_tag_measurable_element FOREIGN KEY (measurable_element_id) REFERENCES measurable_element (id);


CREATE TABLE checkpoint_tag (
  id                 SERIAL PRIMARY KEY,
  tag_id             INTEGER NOT NULL,
  checkpoint_id INTEGER NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL ,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL
);

ALTER TABLE ONLY checkpoint_tag
  ADD CONSTRAINT checkpoint_tag_tag FOREIGN KEY (tag_id) REFERENCES tag (id);
ALTER TABLE ONLY checkpoint_tag
  ADD CONSTRAINT checkpoint_tag_checkpoint FOREIGN KEY (checkpoint_id) REFERENCES checkpoint (id);


CREATE TABLE area_of_concern_tag (
  id                 SERIAL PRIMARY KEY,
  tag_id             INTEGER NOT NULL,
  area_of_concern_id INTEGER NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

ALTER TABLE ONLY area_of_concern_tag
  ADD CONSTRAINT area_of_concern_tag_tag FOREIGN KEY (tag_id) REFERENCES tag (id);
ALTER TABLE ONLY area_of_concern_tag
  ADD CONSTRAINT area_of_concern_tag_area_of_concern FOREIGN KEY (area_of_concern_id) REFERENCES area_of_concern (id);


CREATE TABLE standard_tag (
  id                 SERIAL PRIMARY KEY,
  tag_id             INTEGER NOT NULL,
  standard_id INTEGER NOT NULL,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL,
  inactive           BOOLEAN DEFAULT FALSE                                    NOT NULL,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

ALTER TABLE ONLY standard_tag
  ADD CONSTRAINT standard_tag_tag FOREIGN KEY (tag_id) REFERENCES tag (id);
ALTER TABLE ONLY standard_tag
  ADD CONSTRAINT standard_tag_standard FOREIGN KEY (standard_id) REFERENCES standard (id);

CREATE TABLE deployment_app_configuration (
  id                 SERIAL PRIMARY KEY,
  uuid               UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  version            INTEGER                                                  NOT NULL,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  settings_enabled   BOOLEAN                                                  NULL,
  seed_data_packaged BOOLEAN                                                  NULL
);
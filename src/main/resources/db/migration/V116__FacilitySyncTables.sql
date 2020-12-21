CREATE TABLE missing_nin_entity_in_local
(
  id                 SERIAL PRIMARY KEY,
  name               varchar(255)                                             not null,
  type               varchar(100)                                             not null,
  created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

CREATE TABLE nin_sync_details
(
  id                            SERIAL PRIMARY KEY,
  type                          varchar(100)                                             not null,
  offset_successfully_processed int                                                      not null,
  created_date                  TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL,
  last_modified_date            TIMESTAMP WITHOUT TIME ZONE DEFAULT now() :: TIMESTAMP   NOT NULL
);

insert into nin_sync_details (type, offset_successfully_processed) values ('FacilityMetadata', 0);
insert into nin_sync_details (type, offset_successfully_processed) values ('Facility', 0);
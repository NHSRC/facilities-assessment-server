CREATE TABLE scoring_process_detail (
  id                     SERIAL PRIMARY KEY,
  uuid                   UUID DEFAULT uuid_generate_v4() UNIQUE                   NOT NULL,
  last_scored_until TIMESTAMP WITHOUT TIME ZONE                              NOT NULL
);
insert into scoring_process_detail (last_scored_until, uuid) values (to_timestamp(0), '6773c069-54d6-40ae-9aca-9bddc2e91d43');

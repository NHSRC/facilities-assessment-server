-- didn't plan for release specific migrations hence had to do this using repeatable migrations
DO $$
BEGIN
  BEGIN
    alter table facility_assessment add column assessor_name varchar(255);
    EXCEPTION
    WHEN duplicate_column THEN RAISE NOTICE 'column <column_name> already exists in <table_name>.';
  END;
END;
$$
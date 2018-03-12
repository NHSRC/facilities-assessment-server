ALTER TABLE indicator_definition ADD COLUMN sort_order INTEGER NOT NULL DEFAULT -1;
ALTER TABLE indicator_definition ADD COLUMN description VARCHAR(255) NULL;

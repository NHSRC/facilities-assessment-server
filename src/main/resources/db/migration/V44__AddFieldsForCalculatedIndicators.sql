ALTER TABLE indicator_definition ADD COLUMN calculated BOOLEAN DEFAULT FALSE NOT NULL;
ALTER TABLE indicator_definition ADD COLUMN inactive BOOLEAN DEFAULT FALSE NOT NULL;
ALTER TABLE indicator_definition DROP COLUMN numerator;
ALTER TABLE indicator_definition DROP COLUMN denominator;
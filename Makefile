reset-db:
	-psql postgres -c 'drop database facilities_assessment';
	-psql postgres -c 'create database facilities_assessment with owner nhsrc';
	-psql facilities_assessment -c 'create extension if not exists "uuid-ossp"';

reset-test-db:
	-psql postgres -c 'drop database facilities_assessment_test';
	-psql postgres -c 'create database facilities_assessment_test with owner nhsrc';
	-psql facilities_assessment_test -c 'create extension if not exists "uuid-ossp"';

init-db:
	-psql postgres -c "create user nhsrc with password 'password'";
	-psql postgres -c 'create database facilities_assessment with owner nhsrc';

seed-db:

	-psql -Unhsrc facilities_assessment < '${CURDIR}/src/test/resources/db/migration/R__Create_Test_Data.sql'
	-psql -Unhsrc facilities_assessment < '${CURDIR}/src/test/resources/db/migration/R__Delete_Test_Data.sql'
	-psql -Unhsrc facilities_assessment < src/test/resources/setup.sql


run:
	./gradlew bootRun

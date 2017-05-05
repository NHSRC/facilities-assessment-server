reset-db:
	-psql -h localhost -U postgres postgres -c 'drop database facilities_assessment';
	-psql -h localhost -U postgres postgres -c 'create database facilities_assessment with owner nhsrc';
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment -schemas=public clean
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment -schemas=public -locations=filesystem:./src/main/resources/db/migration/ migrate

reset-test-db:
	-psql postgres -c 'drop database facilities_assessment_test';
	-psql postgres -c 'create database facilities_assessment_test with owner nhsrc';
	-psql facilities_assessment_test -c 'create extension if not exists "uuid-ossp"';
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment_test -schemas=public clean
    	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment_test -schemas=public -locations=filesystem:./src/main/resources/db/migration/ migrate

init-db:
	-psql -h localhost postgres -c "create user nhsrc with password 'password'";
	-psql -h localhost postgres -c 'create database facilities_assessment with owner nhsrc';
	-psql -h localhost facilities_assessment -c 'create extension if not exists "uuid-ossp"';

init-test-db:
	-psql -h localhost postgres -c "create user nhsrc with password 'password'";
	-psql -h localhost postgres -c 'create database facilities_assessment_test with owner nhsrc';

seed-db:
	-psql -Unhsrc facilities_assessment < src/test/resources/db/migration/R__Create_Test_State_Data.sql
	-psql -Unhsrc facilities_assessment < src/test/resources/db/migration/R__Create_Test_Ref_Data.sql
	-psql -Unhsrc facilities_assessment < src/test/resources/db/migration/R__Delete_Test_Data.sql
	-psql -Unhsrc facilities_assessment < src/test/resources/setup.sql

run:
	./gradlew bootRun

help: ## This help dialog.
	@IFS=$$'\n' ; \
	help_lines=(`fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//'`); \
	for help_line in $${help_lines[@]}; do \
	    IFS=$$'#' ; \
	    help_split=($$help_line) ; \
	    help_command=`echo $${help_split[0]} | sed -e 's/^ *//' -e 's/ *$$//'` ; \
	    help_info=`echo $${help_split[2]} | sed -e 's/^ *//' -e 's/ *$$//'` ; \
	    printf "%-30s %s\n" $$help_command $$help_info ; \
	done

nhsrc_db := facilities_assessment_nhsrc
jss_db := facilities_assessment_cg

# <db>
init_db:
	-psql postgres -c "create user nhsrc with password 'password'";

reset_db:
	psql postgres -c "SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '$(database)' AND pid <> pg_backend_pid()"
	-psql postgres -c 'drop database $(database)';
	-psql postgres -c 'create database $(database) with owner nhsrc';
	-psql $(database) -c 'create extension if not exists "uuid-ossp"';
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/$(database) -schemas=public clean
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/$(database) -schemas=public -locations=filesystem:./src/main/resources/db/migration/ migrate

restore_db:
	-psql postgres -c 'drop database $(database)';
	-psql postgres -c 'create database $(database) with owner nhsrc';
	-psql $(database) < $(dump)
# </db>

# <test_db>
init_test_db:
	-psql postgres -c "create user nhsrc with password 'password'";
	-psql postgres -c 'create database facilities_assessment_test with owner nhsrc';

reset_test_db:
	-psql postgres -c 'drop database facilities_assessment_test';
	-psql postgres -c 'create database facilities_assessment_test with owner nhsrc';
	-psql facilities_assessment_test -c 'create extension if not exists "uuid-ossp"';
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment_test -schemas=public clean
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment_test -schemas=public -locations=filesystem:./src/main/resources/db/migration/ migrate

seed_test_db:
	-psql -Unhsrc facilities_assessment < src/test/resources/db/migration/R__Delete_Test_Data.sql
	-psql -Unhsrc facilities_assessment < src/test/resources/db/migration/R__Create_Test_State_Data.sql
	-psql -Unhsrc facilities_assessment < src/test/resources/db/migration/R__Create_Test_Ref_Data.sql
	-psql -Unhsrc facilities_assessment < src/test/resources/setup.sql
# </test_db>

# <schema>
schema_clean:
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment -schemas=public clean

schema_migrate: ## Requires argument - db
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/$(db) -schemas=public -locations=filesystem:./src/main/resources/db/migration/ migrate
# </schema>

# <server>
binary:
	./gradlew build -x test

run_server:
	./gradlew bootRun

run_server_nhsrc: binary
	java -jar build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar --database=facilities_assessment_nhsrc --server.port=6001

run_server_jss: binary
	java -jar build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar --database=facilities_assessment_cg --server.port=6001
# </server>


# <scenario>
create_empty_db_nhsrc:
	make reset_db database=$(nhsrc_db)
	psql -Unhsrc $(nhsrc_db) < src/test/resources/deleteDefaultData.sql
# </scenario>

clean:
	./gradlew clean

clear_responses:
	rm responses/*.json
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

rr_version := 9
reference_response_folder := ../reference-data/nhsrc/output/recorded-response/jsons/$(rr_version)
client_response_folder := ../facilities-assessment-android-client/nhsrc/output/recorded-response/jsons/$(rr_version)
port := 6001

define _run_server
	java -jar build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar --database=facilities_assessment_$1 --server.port=6002 --server.http.port=6001 --recording.mode=$2 --fa.secure=$3
endef

define _tail_server_qa
	ssh $1 "tail -f /home/app/qa-server/facilities-assessment-host/app-servers/log/facilities_assessment.log"
endef

define _tail_server_prod
	ssh $1 "tail -n 500 -f /home/app/facilities-assessment-host/app-servers/log/facilities_assessment.log"
endef

define _deploy_qa
	ssh $1 "sudo service qa-fab stop"
	ssh $1 "rm -rf /home/app/qa-server/facilities-assessment-host/app-servers/*.jar"
	scp build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar $1:/home/app/qa-server/facilities-assessment-host/app-servers/facilities-assessment-server-0.0.1-SNAPSHOT.jar
	ssh $1 "sudo service qa-fab start"
	$(call _tail_server_qa,$1)
endef

define _deploy_prod
	ssh $1 "sudo service fab stop"
	ssh $1 "cp -f /home/app/facilities-assessment-host/app-servers/facilities-assessment-server-0.0.1-SNAPSHOT.jar /tmp/"
	ssh $1 "rm -rf /home/app/facilities-assessment-host/app-servers/*.jar"
	scp build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar $1:/home/app/facilities-assessment-host/app-servers/facilities-assessment-server-0.0.1-SNAPSHOT.jar
	ssh $1 "sudo service fab start"
	$(call _tail_server_prod,$1)
endef

define _debug_server
	java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar --database=facilities_assessment_$1 --server.port=6002 --server.http.port=6001 --recording.mode=$2 --fa.secure=$3
endef

define _restore_db
	psql postgres -c "SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '$1' AND pid <> pg_backend_pid()"
	-psql postgres -c "create user nhsrc with password 'password'";
	psql $(database) -c 'create extension if not exists "uuid-ossp"'
	psql postgres -c 'drop database $1';
	psql postgres -c 'create database $1 with owner nhsrc';
	psql $1 < $2
endef

# <test_db>
init_test_db:
	-psql postgres -c "create user nhsrc with password 'password'";
	-psql postgres -c 'create database facilities_assessment_test with owner nhsrc';

reset_test_db:
	-psql -h localhost postgres -c 'drop database facilities_assessment_test';
	-psql -h localhost postgres -c "create user nhsrc with password 'password'";
	-psql -h localhost postgres -c 'create database facilities_assessment_test with owner nhsrc';
	-psql -h localhost facilities_assessment_test -c 'create extension if not exists "uuid-ossp"';
#	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment_test -schemas=public clean
#	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment_test -schemas=public -locations=filesystem:./src/main/resources/db/migration/ migrate

seed_test_db:
	-psql -Unhsrc facilities_assessment < src/test/resources/db/migration/R__Delete_Test_Data.sql
	-psql -Unhsrc facilities_assessment < src/test/resources/db/migration/R__Create_Test_State_Data.sql
	-psql -Unhsrc facilities_assessment < src/test/resources/db/migration/R__Create_Test_Ref_Data.sql
	-psql -Unhsrc facilities_assessment < src/test/resources/setup.sql
# </test_db>

# <schema>
schema_clean:
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment -schemas=public clean

schema_migrate:
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/$(database) -schemas=public -locations=filesystem:./src/main/resources/db/migration/ migrate
# </schema>

# <server>
build_server:
	./gradlew clean build -x test --offline

run_server_nhsrc: build_server
	$(call _run_server,nhsrc,false,true)

debug_server_nhsrc: build_server
	$(call _debug_server,nhsrc,false,true)

run_server_nhsrc_insecure: build_server
	$(call _run_server,nhsrc,false,false)

run_server_jss: build_server
	$(call _run_server,cg,false,false)

debug_server_jss: build_server
	$(call _debug_server,cg,false,false)

run_server_nhsrc_in_recording: clear_responses build_server
	$(call _run_server,nhsrc,true,false)

test_server: reset_test_db
	./gradlew clean build

open_test_results:
	open build/reports/tests/index.html
# </server>
clean:
	./gradlew clean

# DEPLOY
deploy_to_jss_qa: build_server
	$(call _deploy_qa,igunatmac)

deploy_to_nhsrc_qa: build_server
	$(call _deploy_qa,gunak-other)

deploy_to_jss_prod: build_server
	$(call _deploy_prod,igunatmac)

deploy_to_nhsrc_prod: build_server
	$(call _deploy_prod,gunak-main)

tail_server_jss_qa:
	ssh igunatmac "tail -f /home/app/qa-server/facilities-assessment-host/app-servers/log/facilities_assessment.log"

tail_server_jss_prod:
	$(call _tail_server_prod,igunatmac)

tail_server_nhsrc_prod:
	$(call _tail_server_prod,gunak-main)
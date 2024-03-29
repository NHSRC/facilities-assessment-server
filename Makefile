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

# HOST DIR
define _set_host_dir
	$(eval host_dir := /home/app/$1/facilities-assessment-host)
endef

NIN_API_KEY=dummy

set_host_dir_prod:
	$(call _set_host_dir,)

set_host_dir_qa:
	$(call _set_host_dir,qa)

define _run_server
	FA_ENV=dev java -jar build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar --database=facilities_assessment_$1 --server.port=6002 --server.http.port=6001 --cron.main="0 0 0 ? * * 2099" --database.host=localhost
endef

define _run_server_prod_metabase
	FA_ENV=dev FA_METABASE_URL=https://gunak.nhsrcindia.org:3000 java -jar build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar --database=facilities_assessment_$1 --server.port=6002 --server.http.port=6001 --cron.main="0 0 0 ? * * 2099" --database.host=localhost
endef

define _run_server_background
	echo "Using API Key - $(NIN_API_KEY)"
	FA_ENV=dev java -jar build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar --facility.download.job.enabled=true --database.host=localhost --database=facilities_assessment_$1 --server.port=6002 --server.http.port=6001 --cron.main="0/3 * * * * ?" --nin.apiKey=$(NIN_API_KEY)
endef

define _debug_server_background
	FA_ENV=dev java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar --database=facilities_assessment_$1 --server.port=6002 --server.http.port=6001 --cron.main="0/3 * * * * ?" --database.host=localhost
endef

define _deploy_qa
	ssh $1 "rm -rf /home/app/qa-server/facilities-assessment-host/app-servers/*.jar"
	scp build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar $1:/home/app/qa-server/facilities-assessment-host/app-servers/facilities-assessment-server-0.0.1-SNAPSHOT.jar
endef

define _deploy_prod
	ssh $1 "cp -f /home/app/facilities-assessment-host/app-servers/facilities-assessment-server-0.0.1-SNAPSHOT.jar /tmp/"
	scp build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar $1:/home/app/facilities-assessment-host/app-servers/facilities-assessment-server-0.0.1-SNAPSHOT.jar
endef

define _stop_service
	-ssh $1 "sudo systemctl stop $2"
endef

define _start_service
	ssh $1 "sudo systemctl start $2"
endef

define _restart_service
	-ssh $1 "sudo systemctl restart $2"
endef

define _debug_server
	FA_ENV=dev java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar build/libs/facilities-assessment-server-0.0.1-SNAPSHOT.jar --database=facilities_assessment_$1 --server.port=6002 --server.http.port=6001 --recording.mode=$2 --cron.main="0 0 0 ? * * 2099" --database.host=localhost
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

schema_migrate_nhsrc:
	flyway -user=nhsrc -password=password -url=jdbc:postgresql://localhost:5432/facilities_assessment_nhsrc -schemas=public -locations=filesystem:./src/main/resources/db/migration/ migrate
# </schema>

# <server>
download_dependencies:
	./gradlew compileJava compileTestJava


build_server_online:
	./gradlew clean build -x test

build_server:
	./gradlew clean build -x test --offline

run_server_nhsrc: build_server
	$(call _run_server,nhsrc,false)

run_server_nhsrc_prod_metabase: build_server
	$(call _run_server_prod_metabase,nhsrc,false)

run_server_background_nhsrc: build_server
	$(call _run_server_background,nhsrc,false)

debug_server_background_nhsrc: build_server
	$(call _debug_server_background,nhsrc,false)

debug_server_nhsrc: build_server
	$(call _debug_server,nhsrc,false)

run_server_jss: build_server
	$(call _run_server,cg,false)

debug_server_jss: build_server
	$(call _debug_server,cg,false)

test_server_only:
	-./gradlew clean build --offline

test_server_only_online:
	-./gradlew clean build

test_server: reset_test_db test_server_only open_test_results
test_server_online: reset_test_db test_server_only_online open_test_results

open_test_results:
ifeq ($(shell uname),Darwin)
	open build/reports/tests/test/index.html
endif

temp:
	@echo $(shell uname)
# </server>
clean:
	./gradlew clean

clean_file_upload_reports:
	rm log/*.html

# DEPLOY
deploy_to_jss_qa: build_server
	$(call _deploy_qa,igunatmac)

deploy_to_nhsrc_qa: build_server
	$(call _deploy_qa,gunak-other)

deploy_to_jss_prod: build_server
	$(call _deploy_prod,igunatmac)

deploy_to_nhsrc_prod: build_server
	$(call _deploy_prod,gunak-main)

# Logs
define _tail_server
	ssh $1 "tail -n 500 -f /home/app/$2/facilities-assessment-host/app-servers/log/facilities_assessment.log"
endef

tail_access_log:
	tail -f log/access*

tail_server_jss_qa:
	$(call _tail_server,igunatmac,qa-server)

tail_server_jss_prod:
	$(call _tail_server,igunatmac,)

tail_server_nhsrc_prod:
	$(call _tail_server,gunak-main,)

tail_server_nhsrc_qa:
	$(call _tail_server,gunak-other,qa-server)

inspect_log_nhsrc_prod: set_host_dir_prod
	ssh gunak-main "ls -lt $(host_dir)/app-servers/log"

download_log_nhsrc_prod: set_host_dir_prod ## Param [{extension: additional extension for the file like .1, .2}]
	scp gunak-main:$(host_dir)/app-servers/log/facilities_assessment.log$(extension) log/
	open log/facilities_assessment.log$(extension)

# Service stop/start/restart
restart_service_nhsrc_qa:
	$(call _restart_service,gunak-other,qa-fab)

restart_service_nhsrc_prod:
	$(call _restart_service,gunak-main,fab)

stop_service_nhsrc_qa:
	$(call _stop_service,gunak-other,qa-fab)

restart_service_jss_prod:
	$(call _restart_service,igunatmac,fab)

stop_service_nhsrc_prod:
	$(call _stop_service,gunak-main,fab)

stop_service_jss_prod:
	$(call _stop_service,igunatmac,fab)

.foo:
	@echo ".foo"

bar:
	@echo "bar"

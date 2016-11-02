reset-db:
	-psql postgres -c 'drop database facilities_assessment';
	-psql postgres -c 'create database facilities_assessment with owner nhsrc';
	-psql facilities_assessment -c 'create extension if not exists "uuid-ossp"';

init-db:
	-psql postgres -c 'create user nhsrc with password';
	-psql postgres -c 'create database facilities_assessment with owner nhsrc';

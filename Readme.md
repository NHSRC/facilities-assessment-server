# Facilities Assessment Server
### Setup development environment
1. Download and install Postgres Server version 9.5 or higher in 9.x
2. Install Java version 8
3. Ensure that Make utility is available.
4. To test whether you set up is working fine from command line in the root folder of the project run: `make build_server_online test_server`. For automated test facilities_assessment_test is created.  
5. For production data facilities_assessment_nhsrc database is used - which you can create from a dump. Once your database is created from the dump, you can run the server using `make run_server_nhsrc`. The server is started on port 6001. You can check whether the server is running by http://localhost:6001/api/ping which should response with text pong.

### Background jobs
Gunak server has one background job which is not run by the command in step 5. To run the server with background job use command `make run_server_background_nhsrc`. This will trigger the job every three seconds. This job performs two tasks:
1. Assessment scoring job - to score any new assessment submitted.
2. Facility download job - to download any new facilities created in the nin database. You will require a valid NIN API key.


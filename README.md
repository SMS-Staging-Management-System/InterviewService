# InterviewService
Interview API for Staging Management System for Revature.


# Step 1 - Set up DB and Interview Service
1. You will need to create your own PostgreSQL DB on either your local machine or on a RDS. 
2. Figure out the schema necessary for this service. For development use "interview_service" this information is located in the data.sql file.
3. Will need to run the scripts included on that schema. For development there is a sql script in the top level of this repository named data.sql. Run this set of PostgreSQL commands using a database manager.
4. All environment variables will need to be set. 
    * "DEPLOYMENT_STAGE": "dev",
    * "COGNITO_KEY": key from Cognito,
    * "COGNITO_URL": URL for Cognito,
    * "DB_URL": The actual DB URL - e.g. "jdbc:postgresql://localhost:5432/postgres" if your database is named postgres, 
    * "DB_USERNAME": DB username -e.g. "postgres",
    * "DB_PASSWORD": DB password -e.g. "password",
    * "DB_SCHEMA": Schema where you have the tables saved - for dev this is probably interview_service, 
    * "GATEWAY_URL": Gateway or Discovery Service URL (i.e. localhost:port)
5. Start running the Gateway Service and the Service Registry (requires addiional cognito config) - for development you just need the master branch gateway service (09/06/19) which doesn't include Eureka.
6. Start up this microservice
7. (Optional) You will probably need to start up the User Service to get all of the Cohorts and Associates for New Interview Component on the UI (requires addiional cognito and database config)

# Step 2 - Set up UI
1. npm install - you may need to additionally install node-sass
2. npm start
  no additional environment variable configuration if running services locally

# Bugs
* Feign calls need to be done after pagination to optimize and speed up calls. 
* test endpoint no longer required

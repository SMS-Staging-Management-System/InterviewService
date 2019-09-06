# InterviewService
Interview API for Staging Management System for Revature.


# Step 1 - Set up DB and Interview Service
1. You will need to create your own PostgreSQL DB on either your local machine or on a RDS. 
2. Figure out the schema necessary for this service. 
3. Will need to run the scripts included on that schema. 
4. All environment variables will need to be set. 
    * "DEPLOYMENT_STAGE": "dev",
    * "COGNITO_KEY": "Key from Cognito",
    * "COGNITO_URL": "URL for Cognito",
    * "DB_URL": "The actual DB URL",
    * "DB_USERNAME": "DB username",
    * "DB_PASSWORD": "DB password",
    * "DB_SCHEMA": "Schema where you have the tables saved", 
    * "GATEWAY_URL": "Gateway or Discovery Service URL (i.e. localhost:port")
5. Start running the Gateway Service and the Service Registry (requires addiional cognito config)
6. Start up this microservice
7. (Optional) You will probably need to start up the User Service to get all of the Cohorts and Associates for New Interview Component on the UI (requires addiional cognito and database config)

# Step 2 - Set up UI
1. npm install
2. npm start
  no additional environment variable configuration if running services locally
  
# Backend Filter Implementation - Spring Data Specification
* Utilized Spring Data Sepcification which is a High level interface that implements the Criteria API!
* Implemented the Specification interface and its toPredicate abstract method to define reusable predicates over entities.
* Executed defined predicates via Criteria wrapped into Specification objects that were then passed as arguments to custom Spring Data query methods.
   * The toPredicate implementation handles user input with key, value pairs which are column, value pairs respectively.
      * example: findAll(Specification spec)
         * The resulting query is: 'SELECT * from Table/root<EntityType> WHERE(provided by toPredicate) column/key LIKE %value%'
            *% wild card allows dynamic querys with partial values, example: fo will return food
* The previous interation needed corresponding lines of code for each and every filter but with Specification we can now handle any search string dynamically.

# Bugs
* Feign calls need to be done after pagination to optimize and speed up calls. 
* test endpoint no longer required

# InterviewService
Interview API for Staging Management System for Revature.


<<<<<<< HEAD
# Set up DB and Interview Service
1. You will need to create your own PostgreSQL DB on either your local machine or on a RDS.
2. Run the scripts included to generate the scema and tables. 

# Environment Variables


url for interracting with cognito endpoints  
```COGNITO_URL: ask blake```  

key required for certain cognito endpoints  
```COGNITO_KEY: ask blake```

Password to access the database  
```DB_PASSWORD: password```

Schema for the database  
```DB_SCHEMA: interview_service```

Url pointing to the database that the service will use  
```DB_URL: jdbc:postgresql://localhost:5432/postgres```

Username to log into the database   
```DB_USERNAME: username```

The default gateway can be changed in the application.yml file.
Please set this stage to prod for production or dev for development mode. Setting to dev will void auth requirements.  
```DEPLOYMENT_STAGE: dev```

### The below environment variables are only needed for production
Gateway URL  
```GATEWAY_URL: ask blake if needed```
=======
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
>>>>>>> acecf591dc9ba3168e9ac41ddafba2101906e8a3

# Bugs
* Feign calls need to be done after pagination to optimize and speed up calls. 
* test endpoint no longer required

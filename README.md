# user-management-service


This service is responsible for managing users and their roles.

## How it works

All the user related information is stored in the database.

The service implement spring security to secure the endpoints by letting the security service
authenticate the user and retrieving the user details from the token. The user details are then
used to authorize the user based on the provided defaultRole.

This application can be used as a user creation, authentication/authorization application for a B2C usecase

This service will have the following features or functionalities

1. user registration
2. user password setting
3. user password reset
4. user login - creates a session and return a JWT
5. user (session) validation and increases session timeout
6. Each new user login creates a new session with new deviceId and there is a maximum number of active devices possible
   for a user
7. Logout
8. Stores (Creates/Modifies/Deletes/returns) user permissions.
9. Have concept of super-user and normal user : super-user can give permissions to normal user
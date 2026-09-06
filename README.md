This application can be used as a user creation, authentication/authorization application for a B2C usecase

This service will have the following features or functionalities

1. user registration
2. user password setting
3. user password reset
4. user login - creates a session and return a Token
5. user (session) validation and increases session timeout
6. Each new user login creates a new session with new Token and there is a maximum number of active devices possible
   for a user
7. Logout
8. Stores (Creates/Modifies/Deletes/returns) user permissions.
9. Have concept of super-user and normal user : super-user can give permissions to normal user


TABLES

1. users table
id - automatically generated and incremented pK (On delete cascade)
permalink - unique indexed string generated from firstName and lastName append with random digits
firstName
lastName
email - unique Index
passwordSalt
HashedPassword
isSuperUser
lastPasswordResetDate
CreatedAt
UpdatedAt
locked
resetToken
resetTokenExpiry

2. sessions table
id - automatically generated and incremented pK
userId - integer foreign Key from user table (on delete cascade)
HashAppToken - string uniqueIndexed app token
expiryTime - time - indexed
createdAt - time
updatedAt - time
Index on (userID, expiryTime)

3. user_permissions
id - automatically generated and increment pK
userId - integer fk form user table (On delete cascade)
permissionId - integer fK from permissions table (On delete cascade)
createAt - time
updateAt - time
UniqueIndex on (userId, permissionId)

4. permissions
id - automatically generated permission id
permission - permission String representing the permission - Unique Index
createdAt - time
updatedAt - time


APIs to be made
1. /users POST API for user-creation (No auth required)
When some user submits their data an email for password re-setting is sent to them
2. /users/initiatePasswordReset POST (No auth Required) (Email in body)
3. /users/performPasswordReset POST (password Reset Token must be sent in Bearer)
4. /users/login POST (username and password sent in Basic Auth)
5. /users/validate_auth_token POST (AuthToken sent in Bearer)
6. /users/{permalink}/permissions PUT (Only Super User Has access and may authenticate via Basic Auth or Bearer)
7. /users/{permalink}/permissions PATCH (Only Super User Has access and may authenticate via Basic Auth or Bearer)
8. /users/{permalink}/permissions GET (Super User OR The user himself should be authorized to see)
9. /permissions POST (Super User only)
10. /permissions/{name} DELETE (Super User only)
11. /users/logout POST (Bearer token must be sent)
12. /users/{permalink}/status (Super User only send locked true/false in body)
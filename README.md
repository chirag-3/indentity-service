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
upodatedAt - time


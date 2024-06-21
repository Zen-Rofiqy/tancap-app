
# API Reference
This API provides endpoints for user registration, login, logout, and retrieving a catalog of sign language images with descriptions.

## Base URL

tancap.et.r.appspot.com

## Register an Account
Registers a new user.

### Request

```http
  POST /register
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | "your_username" |
| `email` | `email` | "your_email@example.com" |
| `password` | `string` | "your_password" |

### Response (Success - 201 Created):

````
{
  "message": "User registered successfully"
}
````

### Response (Error - 409 Conflict):

````
{
  "message": "Credentials already exists"
}
````

### Response (Error - 500 Internal Server Error):
````
{
  "message": "Error registering user"
}
````

## Login to an Account
Authenticates a user and returns a JWT.

### Request

```http
  POST /login
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email` | `email` | "your_email@example.com" |
| `password` | `string` | "your_password" |

### Response (Success - 200 OK):

````
{
  "token": "your_jwt_token"
}
````

### Response (Error - 401 Unauthorized):

````
{
  "message": "Invalid credentials"
}
````

## Logout of an Account
Logs out the user. (Client-side: Please remove the stored JWT)

```http
  POST /logout
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `-` | `-` | - |
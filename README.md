# About

Application demonstrates a basic configuration and implementation of JWT with Spring Security.

# Library

| Library                 | Version |
|-------------------------|---------|
| Java                    | 17      |
| Spring Boot             | 3.1.0   |
| Spring                  | 6.1.0   |
| Lombok                  | 1.18.26 |
| GSON                    | 2.10.1  |
| JWT                     | 0.11.5  |
| H2 Database (in memory) | 2.1.214 |
| BDD (Cucumber)          | 7.12.0  |
| JSON Path Assert        | 2.4.0   |

# Usage

- Endpoints
    - POST /account/v1/register : Registers a user and returns a JWT Token
      ```
        {
          "firstName": "YOUR NAME",
          "lastName" : "YOUR LAST NAME",
          "email" : "YOUR EMAIL",
          "password": "YOUR PASSWORD"
          "role":  "USER or ADMIN"
        }
      ```
    - POST /account/v1/generate : Generates a JWT Token for a provided email and password
      ```
        {
          "email" : "YOUR EMAIL",
          "password": "YOUR PASSWORD"
        }
      ```
    - GET /v1/greet : Secured resource for ADMIN or USER role that greets the user for a valid authenticated JWT Token
      - Header:
      ```
        Authorization: Bearer <YOUR JWT TOKEN>
      ```
      - Response:
      ```
        {
          "msg": "Welcome <YOUR FIRST NAME> <YOUR LAST NAME>"
        }
      ```
    - GET /v1/users : Secured resource for ADMIN role that lists the user for a valid authenticated JWT Token
      - Header:
      ```
        Authorization: Bearer <YOUR JWT TOKEN>
      ```
      - Response:
      ```
        [
         {
          "name": "FIRSTNAME LASTNAME",
          "email": "YOUR EMAIL"          
         }
        ]
      ```
    - DB: localhost:<APP_PORT>/h2_console

# BDD

- The project uses Cucumber to do BDD with spring
- Execute <code>mvn test</code> to run the tests and find reports
    - Features: <code>target/cucumber-html-reports/feature-overview.html</code>
    - Code Coverage: <code>target/site/index.html</code>
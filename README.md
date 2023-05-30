# About

Application demonstrates a basic configuration and implementation of JWT with Spring Security.

# Library

| Library                 | Version |
|-------------------------|---------|
| Java                    | 17      |
| Spring Boot             | 3.1.0   |
| Spring                  | 6.1.0   |
| Lombok                  | 1.18.26 |
| JWT                     | 0.11.5  |
| H2 Database (in memory) | 2.1.214 |
| BDD (Cucumber)          | 7.12.0  |

# Usage

- Endpoints
    - /account/v1/register : Registers a user and returns a JWT Token
      ```
        {
          "firstName": "YOUR NAME",
          "lastName" : "YOUR LAST NAME",
          "email" : "YOUR EMAIL",
          "password": "YOUR PASSWORD"
        }
      ```
    - /account/v1/generate : Generates a JWT Token for a provided email and password
      ```
        {
          "email" : "YOUR EMAIL",
          "password": "YOUR PASSWORD"
        }
      ```
    - /v1/greet : Secured resource that greets the user for the passed JWT Token (Bearer Token)
    - DB: localhost:<APP_PORT>/h2_console

# BDD

- The project uses Cucumber to do BDD with spring
- Execute <code>mvn test</code> to run the tests and find reports in <code>target/cucumber-html-reports/feature-overview.html</code>
- Relevant code coverage can be found in jacoco reports
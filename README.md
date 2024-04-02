<p align="center">
  <img src="front/src/assets/mdd_logo.png" title="MDD" width="350" height="200" alt="logo">
</p>
This is a web application where users can subscribe to various programming-related topics such as JavaScript, Java, 
Python, Web3, etc. The platform provides a news feed that displays articles related to the topics the user has 
subscribed to. Additionally, users have the capability to write articles and engage with 
the community by posting comments.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Installation](#installation)
    - [Application](#application)
    - [Database](#database)
3. [Operation](#operation)
4. [Testing](#testing)
    - [Unit Tests with Jest](#unit-tests-with-jest)
    - [Integration Tests with JUnit](#integration-tests-with-junit)
5. [API Documentation](#api-documentation)

## Prerequisites

Ensure you have the following installed on your machine:

- [MySQL](https://dev.mysql.com/downloads/installer/)
- [Java 21](https://www.oracle.com/fr/java/technologies/downloads/#java21)
- [Maven](https://maven.apache.org/download.cgi)

## Installation

### Application

To install the application:

1. Clone this repository to your local machine
   `git clone https://github.com/Mihai-Cojusnean/Developpez-une-application-full-stack-complete`.
2. Navigate to the `frontend/` directory and run `npm install` to install frontend dependencies.
3. Navigate to the `backend/` directory and run `mvn install` to install backend dependencies.

### Database

To install the database:

1. Update the application properties in the backend project to connect to your MySQL database.
2. Execute the SQL scripts provided in the `ressources/script.sql` directory to populate some initial data.

## Operation

1. Start the backend server: Navigate to the `backend/`directory and run `mvn spring-boot:run`.
2. Start the frontend server: Navigate to the `frontend/` directory and run `ng serve`.

## Testing

### Unit Tests with Jest

To launch unit tests with Jest:

1. Navigate to the `front/` directory.
2. Run `npm run test` to execute the Jest unit tests.
3. View test results in the console.

### Integration Tests with JUnit

To launch integration tests with JUnit:

1. Navigate to the `back/` directory.
2. Run `mvn test` to execute the JUnit integration tests.
3. Test results will be output to the console, and JUnit XML reports will be generated in the `target/surefire-reports/`
   directory.

## API Documentation
API documentation is available using Swagger. 
Once the application is running, you can access the Swagger UI at http://localhost:8075/swagger-ui/index.html.

# Monde De Dév Fullstack project

![MDD](./front/public/mdd-logo.svg)

ORION, a software development company, aims to create a social network dedicated to developers called "MDD" (Monde de Dév). The social network aims to assist developers in finding employment by encouraging collaboration among peers. 

Before launching it publicly, ORION wants to test an internal minimal version called MVP (Minimum Viable Product). 

The MVP will allow users to subscribe to programming topics, view articles chronologically in their feed, and post articles and comments. You are responsible for developing the MVP, with the assistance of your team including Orlando (your manager), Heidi (a fellow developer), and Juana (the UX designer).

## Table of contents

- [Monde De Dév Fullstack project](#monde-de-dév-fullstack-project)
  - [Table of contents](#table-of-contents)
  - [Project stack:](#project-stack)
    - [Front-End](#front-end)
    - [Back-End](#back-end)
  - [Pre-requisites](#pre-requisites)
    - [Java Development Kit (JDK)](#java-development-kit-jdk)
    - [Apache Maven](#apache-maven)
    - [PostgreSQL](#postgresql)
    - [Node.js](#nodejs)
  - [Configuration](#configuration)
    - [1. Java Development Kit (JDK)](#1-java-development-kit-jdk)
    - [2. PostgreSQL](#2-postgresql)
  - [Installation procedure](#installation-procedure)
    - [Front-End](#front-end-1)
    - [Back-End](#back-end-1)
  - [Dependencies](#dependencies)
    - [Front-End](#front-end-2)
    - [Back-End](#back-end-2)
  - [API documentation](#api-documentation)
    - [Open endpoints](#open-endpoints)
    - [JWT required endpoints](#jwt-required-endpoints)
  - [Miscellaneous](#miscellaneous)

## Project stack:
### Front-End

- HTML
- SASS
- TypeScript
- Angular 17

<a href="https://developer.mozilla.org/en-US/docs/Glossary/HTML5" target="_blank" rel="noreferrer"><img src="https://raw.githubusercontent.com/danielcranney/readme-generator/main/public/icons/skills/html5-colored.svg" width="36" height="36" alt="HTML5" /></a>
<a href="https://sass-lang.com/" target="_blank" rel="noreferrer"><img src="https://raw.githubusercontent.com/danielcranney/readme-generator/main/public/icons/skills/sass-colored.svg" width="36" height="36" alt="Sass" /></a>
<a href="https://www.typescriptlang.org/" target="_blank" rel="noreferrer"><img src="https://raw.githubusercontent.com/danielcranney/readme-generator/main/public/icons/skills/typescript-colored.svg" width="36" height="36" alt="TypeScript" /></a>
<a href="https://angular.dev/" target="_blank" rel="noreferrer"><img src="https://cdn.discordapp.com/attachments/688142925204422899/1208048635066060860/image.png?ex=65e1ddf7&is=65cf68f7&hm=bac39733e54e8c7db598f6aab7f273f4215f993d30916bb000081cee11916bbb&" width="44" height="40" alt="Angular" /></a>


### Back-End

- Java 21
- Spring Boot 3.1.2

<a href="https://www.java.com/en/" target="_blank" rel="noreferrer"><img src="https://raw.githubusercontent.com/devicons/devicon/6910f0503efdd315c8f9b858234310c06e04d9c0/icons/java/java-original.svg" width="36" height="36" alt="Java" /></a>
<a href="https://start.spring.io/" target="_blank" rel="noreferrer"><img src="https://raw.githubusercontent.com/devicons/devicon/6910f0503efdd315c8f9b858234310c06e04d9c0/icons/spring/spring-original.svg" width="36" height="36" alt="Spring" /></a>


## Pre-requisites

Before you begin, ensure that the following software is installed on your system:

### Java Development Kit (JDK)

 You can download the latest version of the [JDK](https://adoptopenjdk.net/) for your platform

### Apache Maven

You'll need [Maven](https://maven.apache.org/) for building and managing the project's dependencies.

### PostgreSQL

Install and set up PostgreSQL as the database for the Back-End. You can install it from [here](https://www.postgresql.org/download/).

### Node.js

Install [Node.js LTS](https://nodejs.org/en) to install the Front-End dependencies.

## Configuration

### 1. Java Development Kit (JDK)

Install Java version 21 using [SDKMAN](https://sdkman.io/), a tool for managing software development kits. SDKMAN simplifies the installation process and version management.

- **Install SDKMAN:**

If you do not have 7zip installed, you can install from [their website](https://www.7-zip.org/)

Then, in a GitBash terminal, run as an administrator these commands:

  ```shell
# To install 7zip
ln -s /c/Program\ Files/7-Zip/7z.exe /c/Program\ Files/Git/mingw64/bin/zip.exe

# To install SDK Man
export SDKMAN_DIR="/c/sdkman" && curl -s "https://get.sdkman.io" | bash
```

To install Java version 21, run the following command:

```shell
# Check that it's available
sdk list java | grep -i '21'

# Install Java version 21
sdk install java 21-oracle
```

Ensure that the Java environment variable is correctly configured on your system. This variable is essential for Java applications to run. You can set it up by following the steps specific to your operating system.

- Windows:

1. Open the System Properties.
2. Click on the `Advanced` tab.
3. Click the `Environment Variables` button.
4. Under `System variables`, create a new variable named `JAVA_HOME`.
5. Add the path to your JDK's binary directory (e.g., `C:\sdkman\candidates\java\[JAVA VERSION NAME]\bin`)
6. Click `OK` to save your changes

Restart your computer and then run the command to verify that you have the correct Java version installed:

```shell
java -version
```

### 2. PostgreSQL

Follow these steps to configure PostgreSQL for your Java application:

Open pgAdmin 4.

Connect to your PostgreSQL Server instance.

Create a new database for your application and add all the tables to your database:

```sql
-- Reset PostgreSQL schema
DROP SCHEMA public CASCADE;

-- Create a new schema 
CREATE SCHEMA public;

-- Tables
CREATE TABLE Users (
    ID INT PRIMARY KEY,
    username VARCHAR(50),
    email VARCHAR(255),
    password TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Themes (
    ID INT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Articles (
    ID INT PRIMARY KEY,
    userID INT,
    themeID INT,
    title VARCHAR(255),
    description TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userID) REFERENCES Users(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (themeID) REFERENCES Themes(ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Comments (
    ID INT PRIMARY KEY,
    userID INT,
    articleID INT,
    comment TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userID) REFERENCES Users(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (articleID) REFERENCES Articles(ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Subscriptions (
    ID INT PRIMARY KEY,
    userID INT,
    themeID INT,
    isSubscribed BOOLEAN,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userID) REFERENCES Users(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (themeID) REFERENCES Themes(ID) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Mock data
INSERT INTO Users (ID, username, email, password)
VALUES 
    (1, 'user', 'user@user.com', '$2a$10$Cugtb5QEITQsbHMuuBWKqecku/5hup5afBWrVqJdU6nN9Ov/wNYy2'),
    (2, 'test', 'test@test.com', '$2a$10$Cugtb5QEITQsbHMuuBWKqecku/5hup5afBWrVqJdU6nN9Ov/wNYy2');

INSERT INTO Themes (ID, title, description)
VALUES 
    (1, 'Python', 'Articles related to Python programming language'),
    (2, 'TypeScript', 'Articles related to TypeScript programming language'),
    (3, 'Java', 'Articles related to Java programming language'),
    (4, 'SEO', 'Articles related to Search Engine Optimization'),
    (5, 'SASS', 'Articles related to SASS (Syntactically Awesome Style Sheets)'),
    (6, 'C++', 'Articles related to C++ programming language');

INSERT INTO Articles (ID, userID, themeID, title, description)
VALUES
    (1, 1, 1, 'Introduction to Python Programming', 'Learn the basics of Python programming language, including variables, loops, and functions.'),
    (2, 1, 2, 'Getting Started with TypeScript', 'Discover how to start using TypeScript for building scalable and maintainable web applications.'),
    (3, 1, 3, 'Java Fundamentals for Beginners', 'Explore the fundamentals of Java programming language, including object-oriented concepts and syntax.'),
    (4, 1, 4, 'Mastering SEO Techniques', 'Learn advanced SEO techniques to improve website ranking and visibility on search engines.'),
    (5, 1, 5, 'SASS: Enhancing CSS Development', 'Explore the features of SASS and how it can streamline CSS development and maintainability.'),
    (6, 1, 6, 'Advanced C++ Programming Concepts', 'Dive into advanced C++ programming concepts, including templates, polymorphism, and memory management.');

INSERT INTO Comments (ID, userID, articleID, comment)
VALUES
    (1, 2, 1, 'Great introduction to Python!'),
    (2, 2, 1, 'I found this article very helpful.'),
    (3, 2, 2, 'Awesome guide to TypeScript!'),
    (4, 2, 5, 'I learned a lot about SASS from this article.'),
    (5, 2, 6, 'C++ programming concepts explained well.'),
    (6, 2, 6, 'Looking forward to more articles on C++.'),
    (7, 2, 6, 'Great article, thank you!');

INSERT INTO Subscriptions (ID, userID, themeID, isSubscribed, createdAt, updatedAt)
VALUES
    (1, 1, 1, TRUE, NOW(), NOW()),
    (2, 1, 3, TRUE, NOW(), NOW()),
    (3, 1, 4, TRUE, NOW(), NOW()),
    (4, 2, 2, TRUE, NOW(), NOW()),
    (5, 2, 5, TRUE, NOW(), NOW());    
```

## Installation procedure

**Cloning the project:**
To clone this repository from GitHub, run the following command: `git clone https://github.com/LePhenix47/Lahouiti_Younes_P5_30122023 .`

### Front-End

1. Install the dependencies:

To start the Angular Front-End project, follow these steps:

- Navigate to the Front-End directory in your terminal:

```shell
cd front
```

- Install project dependencies using npm:

```shell
npm install
```

2. Starting the server

- After the dependencies are installed, you can start the development server by running:

```shell
npm run dev
```

This command will compile the Angular application and start a development server.
You can then access the application in your browser at `http://localhost:4200`.

### Back-End

1. Configure the application in the `application.properties` file

Once you have cloned the repository, you'll need to add the `application.properties` file on the `src/main/resources/` folder containing these properties:

```properties
# PostgreSQL database
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/p6-mdd
spring.datasource.username=postgres
spring.datasource.password=Az&rty1234

# Java Persistence API
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true

# TomCat server
server.port=3001
server.error.include-message=always
server.error.include-binding-errors=always
server.error.include-stacktrace=on_param


# Logger
logging.level.root=INFO
logging.level.org.springframework=INFO
logging.level.com.yourpackage=DEBUG
logging.level.com.openclassrooms=INFO
logging.level.org.springframework.boot.web.embedded.tomcat=INFO
```

2. Install the project dependencies using the following command: `mvn clean install`

3. Run the application using your IDE or by running `mvn spring-boot:run` in the project directory.

## Dependencies

### Front-End

### Back-End

**Core Spring Framework Dependencies:**

- `spring-boot-starter-actuator`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `spring-boot-starter-websocket`
- `spring-boot-devtools`

**Database Related Dependencies:**

- `postgresql`

**Utility Dependencies:**

- `lombok`

**Testing Dependencies:**

- `spring-boot-starter-test`
- `spring-restdocs-mockmvc`
- `spring-security-test`

**Validation Dependencies:**

- `javax.servlet-api`
- `hibernate-validator`

**Json Web Token Dependencies:**

- `io.jsonwebtoken:jjwt-api`
- `io.jsonwebtoken:jjwt-impl`
- `io.jsonwebtoken:jjwt-jackson`

**Code Mapping Tool Dependencies (DTO):**

- `org.mapstruct:mapstruct`
- `org.mapstruct:mapstruct-processor`

## API documentation

You can view the different API endpoints from this table:

### Open endpoints

- `api/auth`:

| HTTP VERB | Endpoint  | Parameters | Request payload                                          | Response payload                     | Description of the reponse           |
| --------- | --------- | ---------- | -------------------------------------------------------- | ------------------------------------ | ------------------------------------ |
| POST      | /register | x          | {  userName: string,  email: string,  password: string } | {  message: string,  token: string } | An object with a token and a message |
| POST      | /login    | x          | {  identifier: string,  password: string }               | {  message: string,  token: string } | An object with a token and a message |

### JWT required endpoints

- `api/articles`:
  
| HTTP VERB | Endpoint    | Parameters                              | Request payload                                          | Response payload                                                                                                                                                                  | Description of the reponse                                            |
| --------- | ----------- | --------------------------------------- | -------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------- |
| GET       | /           | x                                       | x                                                        | {  id: number,  userId: number,  articleId: number,  title: string,  description: string }[]                                                                                      | Array of articles                                                     |
| POST      | /           | x                                       | {  themeId: number, title: string, description: string } | {  message: string }                                                                                                                                                              | A message saying if the article has been succesfully published or not |
| GET       | /:articleId | Id of the article the dev wants to read | x                                                        | {  id: number,  authorName: string,  creationDate: string, // ISO string  theme: string,  title: string,  description: string,  comments: {userName: string, comment: string}[] } | An object containing info about the article and an array of comments  |
| POST      | /comment    | x                                       | {  articleId: number  comment: string }                  | {  message: string }                                                                                                                                                              | A message saying if the comment was sent with success or not          |

- `api/themes`:

| HTTP VERB | Endpoint     | Parameters | Request payload      | Response payload                                                                                                   | Description of the reponse                                   |
| --------- | ------------ | ---------- | -------------------- | ------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------ |
| GET       | /            | x          | x                    | {  id: number,  userId: number,  themeId: number,  title: string,  description: string,  isSubscribed: boolean }[] | Array of all the themes                                      |
| GET       | /subscribed  | x          | x                    | {  id: number,  userId: number,  themeId: number,  title: string,  description: string,  isSubscribed: boolean }[] | Array of all the themes the user is subscribed to            |
| POST      | /subscribe   | x          | {  themeId: number } | {  message: string }                                                                                               | A message saying if the subscription request worked or not   |
| POST      | /unsubscribe | x          | {  themeId: number } | {  message: string }                                                                                               | A message saying if the unsubscription request worked or not |

- `api/user`

| HTTP VERB | Endpoint | Parameters | Request payload                        | Response payload     | Description of the reponse                                             |
| --------- | -------- | ---------- | -------------------------------------- | -------------------- | ---------------------------------------------------------------------- |
| PUT       | /        | x          | {  username: string,  email: string, } | {  message: string } | A message saying if the user info has been successfully updated or not |

## Miscellaneous

<details>
  <summary>🚀 Explore the API on Postman</summary>
  <a href="./front/resources/p6.postman_collection.json">
    You can import the Postman environment to test the different API endpoints
  </a>
</details>

<details>
  <summary>🔗 Link to the original GitHub Repository</summary>
  <a href="https://github.com/OpenClassrooms-Student-Center/Developpez-une-application-full-stack-complete" target="_blank">
    Link to the Back-End and Front-End Code
  </a>
</details>

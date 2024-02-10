# Monde De Dév Fullstack project

![MDD](./front/public/mdd-logo.svg)

This Fullstack project is dedicated to

## Table of contents

- [Monde De Dév Fullstack project](#monde-de-dév-fullstack-project)
  - [Table of contents](#table-of-contents)
  - [Pre-requisites](#pre-requisites)
  - [Configuration](#configuration)
  - [Installation procedure](#installation-procedure)
    - [Front-End](#front-end)
    - [Back-End](#back-end)
  - [Dependencies](#dependencies)
    - [Front-End](#front-end-1)
    - [Back-End](#back-end-1)
  - [API documentation](#api-documentation)
  - [Miscellaneous](#miscellaneous)

## Pre-requisites

Before you begin, ensure that the following software is installed on your system:

- **Java Development Kit (JDK):** You can download the latest version of the [JDK](https://adoptopenjdk.net/) for your platform

- **Apache Maven:** You'll need [Maven](https://maven.apache.org/) for building and managing the project's dependencies.

- **PostgreSQL:** Install and set up PostgreSQL as the database for the Back-End. You can install it from [here](https://www.postgresql.org/download/).

- **Node.js:** Install [Node.js LTS](https://nodejs.org/en) to install the Front-End dependencies.

## Configuration

1. **Java Development Kit (JDK):** Install Java version 21 using [SDKMAN](https://sdkman.io/), a tool for managing software development kits. SDKMAN simplifies the installation process and version management.

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

2. **PostgreSQL:**

Follow these steps to configure PostgreSQL for your Java application:

Open pgAdmin 4.

Connect to your PostgreSQL Server instance.

Create a new database for your application and add all the tables to your database:

```sql
CREATE TABLE Users (
    ID INT PRIMARY KEY,
    username VARCHAR(50),
    email VARCHAR(255),
    password TEXT,
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

CREATE TABLE Themes (
    ID INT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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

INSERT INTO Users (ID, username, email, password)
VALUES (1, 'user', 'user@user.com', 'test!1234');
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

## API documentation

You can view the diffrent API endpoints from this table:

- `api/auth`:

| HTTP VERB | Endpoint  | Parameters | Request payload                                          | Response payload                     | Description of the reponse           |
|-----------|-----------|------------|----------------------------------------------------------|--------------------------------------|--------------------------------------|
| POST      | /register | x          | {  userName: string,  email: string,  password: string } | {  message: string,  token: string } | An object with a token and a message |
| POST      | /login    | x          | {  identifier: string,  password: string }               | {  message: string,  token: string } | An object with a token and a message |

- `api/articles`:
  
| HTTP VERB | Endpoint     | Parameters                                    | Request payload                       | Response payload                                                                                                                                                                  | Description of the reponse                                                        |
|-----------|--------------|-----------------------------------------------|---------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| GET       | /            | x                                             | x                                     | {  id: number,  userId: number,  articleId: number,  title: string,  description: string }[]                                                                | Array of articles                                                            |
| POST      | /            | x                                             | {  theme: string, title: string, description: string } | {  message: string }                                                                                                                                                              | A message saying if the article has been succesfully published or not                               |
| GET       | /:articleId  | Id of the article the dev wants to read | x   | {  id: number,  authorName: string,  creationDate: string, // ISO string  theme: string,  title: string,  description: string,  comments: {userName: string, comment: string}[] }                                         | An object containing info about the article and an array of comments |
| POST      | /comment     | x                                             | {  articleId: number  comment: string } | {  message: string }                                                                                                                                                              | A message saying if the comment was sent with success or not                               |

- `api/themes`:

| HTTP VERB | Endpoint     | Parameters                                    | Request payload                       | Response payload                                                                                                                                                                  | Description of the reponse                                                        |
|-----------|--------------|-----------------------------------------------|---------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| GET       | /            | x                                             | x                                     | {  id: number,  userId: number,  themeId: number,  title: string,  description: string,  isSubscribed: boolean }[]                                                                | Array of subscriptions                                                            |
| POST      | /subscribe   | x                                             | {  themeId: number }                  | {  message: string }                                                                                                                                                              | A message saying if the subscription request worked or not                        |
| POST      | /unsubscribe | x                                             | {  themeId: number }                  | {  message: string }                                                                                                                                                              | A message saying if the unsubscription request worked or not                      |

## Miscellaneous

<details>
  <summary>🔗 Link to the original GitHub Repository</summary>
  <a href="https://github.com/OpenClassrooms-Student-Center/Developpez-une-application-full-stack-complete" target="_blank">
    Link to the Back-End and Front-End Code
  </a>
</details>

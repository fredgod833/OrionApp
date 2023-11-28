# MDD APP

### Prerequisites

> Git
> Node.js
> npm
> Java
> Maven
> MySQL

> First you need to create a mysql databased named 'mddbdd'
> Launch the script 'ressources/script.sql' to create the schema and add some data

## Front ( VS CODE )

Navigate to the front directory.

Install dependencies:

> npm install

Launch a dev server:

> ng serve

The app should run on 'http://localhost:4200/'

### Back ( VS CODE )

Navigate to the back directory.

Launch the server:

> mvn spring-boot:run

Launch the db ( MYSQL )

> sudo service mysql start

### Other

> App: http://localhost:4200/
> Back: http://localhost:8080/
> SwaggerDoc: http://localhost:8080/swagger/swagger-ui/index.html
> ApiDoc: http://localhost:8080/v3/api-docs/

# P6-Full-Stack-reseau-dev

## Where to start 

Clone project from here: https://github.com/Marvin-Silva/Developpez-une-application-full-stack-complete/tree/dev

## Front

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.3.

Don't forget to install your node_modules before starting (`npm install`).

### Go inside folder:
> cd front

Install the dependencies:
> npm install

Launch frontend:
> npm run start

### Lancer les tests :
Jest
> npm run test / npm run test:watch (Follow changes)

Cypress

> npm run e2e

### Generating coverage reports

Jest
> npm run test --coverage

Cypress
> npm run e2e:coverage
> npm run cypress:run (Run all cypress test)

### Reports are generated here :

Jest
>coverage>jest>lcov-report>index.html

Cypress
>coverage>lcov-report>index.html

### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

### Back

Go inside folder:
> cd back

Build and Launch backend:
> mvn spring-boot:run
> /
> by IDE

### Lancer les tests :
Junit 5
> mvn test
> /
> by IDE

### Generating coverage reports

Rapport jacoco
> mvn jacoco:report

### Reports are generated here :

Jacoco
>target>site>jacoco>index.html

Good luck!

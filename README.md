# Clinical Register System

This is just an example project for microservices using spring boot.

## Spring project with microservices

Description of all microservices.

### Eureka

Microservice to register an load balance another microservices.

### Gateway

Microservice to redirect request to the right microservice.

### OAuth2

Microservice to authenticate the users.

### User

Microservice to do the CRUD user.

### Address

Microservice to do the CRUD address.

### Patient

Microservice to do the CRUD patient. A patient has many addresses,
then the patient require Address microservice to create the
related address for the patient.

## Run project

You need Docker and run the next command:

```bash
docker compose --profile all up
```

Go to your browser on `http://localhost:8080`.

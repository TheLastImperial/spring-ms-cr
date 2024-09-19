# Clinical Register System

This is just an example project for microservices using spring boot.

## Spring project with microservices

Description of all microservices.

### Eureka

Microservice to register a load balance another microservices.

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

# Run project

## Docker compose

You need Docker and run the next command:

```bash
docker compose --profile all up
```

Go to your browser on `http://localhost:9091`.

For use the api you can use the URL `http://localhost:9091/api`.

## Minikube

You must start the minikube with the next command.

```bash
minikube start
```

Charge the images to the Minikube container.

```bash
minikube image load prom/prometheus:v2.54.1 grafana/grafana-enterprise:11.2.0 redis:7.2-alpine postgres:16.3 tli/cr-ms-patient:0.0.2 tli/cr-ms-address:0.0.2 tli/cr-ms-user:0.0.2 tli/cr-ms-oauth2:0.0.2 tli/cr-gateway:0.0.2 tli/cr-eureka:0.0.1 tli/cr-ms-frontend:0.0.1
```

Go to `k8` folder and run the `start.sh` file to deploy.

```bash
cd k8
bash start.sh
```

After a few moments you can look the front end running the next command.

```bash
minikube service frontend
```

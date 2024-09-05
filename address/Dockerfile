FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean install package -Dmaven.test.skip

FROM eclipse-temurin:17-jre-alpine AS deploy
COPY --from=builder /app/target/address-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# ./mvnw clean install package -Dmaven.test.skip

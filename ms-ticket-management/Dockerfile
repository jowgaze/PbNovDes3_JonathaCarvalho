FROM maven:3.8.4-openjdk-17 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/ms-ticket-management-0.0.1-SNAPSHOT.jar /app/ms-ticket.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "ms-ticket.jar"]
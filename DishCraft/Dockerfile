FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /project

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src/ /project/src

RUN mvn package

FROM eclipse-temurin:17-jdk-focal
RUN mkdir /app
COPY --from=build /project/target/*.jar /app/app.jar
WORKDIR /app

EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
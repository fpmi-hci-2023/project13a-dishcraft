FROM eclipse-temurin:17-jdk-focal
#VOLUME /tmp
#WORKDIR /app
EXPOSE 8080
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
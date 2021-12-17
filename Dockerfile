FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY . target/user-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app.jar"]

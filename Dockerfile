FROM openjdk:11
ARG JAR_FILE=target/*.jar
EXPOSE 8092
WORKDIR /usr/src/app
COPY target/user-service-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

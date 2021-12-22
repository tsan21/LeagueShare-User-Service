FROM maven:3.8.1-jdk-11

WORKDIR /usr/src/app

COPY . /usr/src/app
RUN mvn clean install -Dmaven.test.skip=true

EXPOSE 8092
CMD [ "sh", "-c", "mvn spring-boot:run" ]

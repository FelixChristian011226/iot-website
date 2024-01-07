FROM openjdk:latest
ADD application.yml application.yml
ADD iot-backend-0.0.1-SNAPSHOT.jar iot-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/iot-backend-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
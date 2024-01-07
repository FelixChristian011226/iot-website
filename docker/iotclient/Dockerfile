FROM openjdk:latest
ADD iot.properties iot.properties
ADD iotclient.jar iotclient.jar
ENTRYPOINT ["java","-jar","/iotclient.jar"]
EXPOSE 8081
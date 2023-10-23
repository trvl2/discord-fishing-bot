FROM openjdk:17
LABEL authors="fxdev.tech"
MAINTAINER fxdev.tech

# Set the working directory in the container
WORKDIR /home/automator

# Copy your Java application JAR file into the container
COPY target/SinkingShipsAutomator-2.0.jar /home/automator/app.jar

ENTRYPOINT ["java", "-jar","/home/automator/app.jar"]
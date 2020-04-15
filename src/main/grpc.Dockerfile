# For Java 8, try this
FROM openjdk:8-jdk-alpine

EXPOSE 6565
# For Java 11, try this
#FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
#ARG JAR_FILE=target\*.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY target/api-service-0.0.1-SNAPSHOT.jar app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
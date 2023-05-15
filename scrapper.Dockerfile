FROM openjdk:17.0.1-jdk-slim

ARG JAR_FILE=./scrapper/target/*SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} scrapper.jar

ENTRYPOINT ["java","-jar","scrapper.jar"]
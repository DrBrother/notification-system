FROM openjdk:17.0.1-jdk-slim

ARG JAR_FILE=./bot/target/*SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} bot.jar

ENTRYPOINT ["java","-jar","bot.jar"]
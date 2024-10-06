# Не ставятся зависимости в контейнер.
# Реализую докеризацию проекта через перенос исполняемого файла
#
#FROM maven:3.9.6 AS build
#
#WORKDIR /app
#
#COPY pom.xml ./
#COPY .mvn .mvn
#COPY mvnw* ./
#
#RUN mvn dependency:go-offline
#
#COPY src ./src
#COPY .env .
#RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY ./target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

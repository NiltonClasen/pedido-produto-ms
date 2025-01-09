# Use an official Maven image to build the application
FROM maven:3.8.5-openjdk-17 AS build

COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jre

# Set the working directory in the container
WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
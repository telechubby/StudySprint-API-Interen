# Use an official Maven image as a base image for building
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the Spring Boot application
RUN mvn clean install -DskipTests

# Use an official OpenJDK runtime as a base image
FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage into the container
COPY --from=build /app/target/API-0.0.1-SNAPSHOT.jar /app/

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "API-0.0.1-SNAPSHOT.jar"]

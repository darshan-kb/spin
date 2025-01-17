# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/game-0.0.1-SNAPSHOT.jar game.jar

# Expose the application port
EXPOSE 8080

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "game.jar"]
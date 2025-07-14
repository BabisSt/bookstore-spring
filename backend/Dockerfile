# Use a base image with Java
FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file
COPY target/endpoint-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]

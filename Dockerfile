# Use a specific Java 17 image for consistency
FROM openjdk:17.0.2-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# --- Optimization: Fix permissions and download dependencies first ---
# Grant execute permission to the maven wrapper
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

# Download dependencies. This layer is only rebuilt if pom.xml changes.
RUN ./mvnw dependency:go-offline

# --- Build the application ---
# Copy the source code
COPY src ./src

# Package the application
RUN ./mvnw clean package -DskipTests

# --- Run the application ---
# Expose the port the app runs on
EXPOSE 8080

# The command to run the JAR file
CMD ["java", "-jar", "target/expensetracker-0.0.1-SNAPSHOT.jar"]
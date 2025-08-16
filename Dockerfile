# Use official Java 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy and build with Maven Wrapper
COPY . .
RUN ./mvnw clean package -DskipTests

# Run the JAR (adjust name if different)
CMD ["java", "-jar", "target/expensetracker-0.0.1-SNAPSHOT.jar"]

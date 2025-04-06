# Stage 1: Build the application
FROM eclipse-temurin:17-jdk AS build

LABEL authors="akshay"

# Set working directory
WORKDIR /app

# Copy Maven wrapper, .mvn directory, and pom.xml
COPY mvnw ./
COPY .mvn/ .mvn/
COPY pom.xml ./

# Ensure Maven wrapper is executable
RUN chmod +x mvnw

# Download dependencies to cache them
RUN ./mvnw dependency:resolve dependency:resolve-plugins

# Copy source code and build the application
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Create a smaller runtime image
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app
COPY . .

# Build the fat JAR (with dependencies)
RUN mvn clean package -DskipTests

# Stage 2: Runtime (minimal JRE only)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the fat JAR from the build stage
COPY --from=builder /app/target/java-compressor-1.0.0-jar-with-dependencies.jar java-compressor.jar

# CLI entrypoint
ENTRYPOINT ["java", "-jar", "java-compressor.jar"]

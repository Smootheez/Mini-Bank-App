# Stage 1: Build the application using your local Gradle wrapper
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy everything, including gradlew and wrapper files
COPY . .

# Make sure the Gradle wrapper is executable
RUN chmod +x ./gradlew

# Build the jar using wrapper (which uses Gradle 8.14)
RUN ./gradlew clean bootJar --no-daemon

# Stage 2: Run with minimal image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Set default envs (override in `docker run` or compose)
ENV SPRING_PROFILE_ACTIVE=prod \
    DB_URL=localhost:5432/mini-bank \
    DB_USER=postgres \
    DB_PASSWORD=postgres

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

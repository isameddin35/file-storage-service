# ===== Stage 1: Build =====
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Gradle wrapper & build scripts first (for caching)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Make gradlew executable
RUN chmod +x gradlew

# Download dependencies (caches this step)
RUN ./gradlew dependencies --no-daemon || return 0

# Copy the rest of the source code
COPY src src

# Build the JAR
RUN ./gradlew clean bootJar --no-daemon

# ===== Stage 2: Run =====
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Let Render/Heroku/etc set PORT, fallback to 8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

# 1) Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /build

# Copy all project files
COPY . .

# Build the jar (skip tests if needed)
RUN mvn clean package -DskipTests

# 2) Run stage
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the jar from the build stage
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

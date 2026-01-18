# Build Stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copy only the pom first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copy the source code and build the jar
COPY src ./src
RUN mvn clean package -DskipTests -B

# Run Stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 3. Copy the resulting jar from the build stage
# Note: Use a wildcard *.jar if you aren't sure of the exact name
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
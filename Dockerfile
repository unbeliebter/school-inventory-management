# Build Stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# Run Stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 1. Postgres installieren
RUN apt-get update && apt-get install -y postgresql postgresql-contrib && rm -rf /var/lib/apt/lists/*

# 2. Die gebaute Jar kopieren
COPY --from=build /app/target/*.jar app.jar

# 3. Ein Start-Skript erstellen (Entrypoint)
# Es startet Postgres, erstellt die DB und startet dann Java
RUN echo '#!/bin/bash \n\
service postgresql start \n\
until pg_isready; do sleep 1; done \n\
su - postgres -c "psql -c \"CREATE DATABASE \\\"schoolInventoryDS\\\";\"" \n\
su - postgres -c "psql -c \"ALTER USER postgres WITH PASSWORD '\''postgres'\'';\"" \n\
java -jar /app/app.jar' > /app/start.sh && chmod +x /app/start.sh

# Da die DB im selben Container läuft, muss die App auf localhost zugreifen
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/schoolInventoryDS
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres

EXPOSE 8080
# Wir nutzen das Skript als Startpunkt
ENTRYPOINT ["/app/start.sh"]
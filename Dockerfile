# --- Build Stage ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

# --- Run Stage ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 1. PostgreSQL 18 Repository hinzufügen und installieren
RUN apt-get update && apt-get install -y wget gnupg2 lsb-release && \
    wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add - && \
    echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list && \
    apt-get update && \
    apt-get install -y postgresql-18 && \
    rm -rf /var/lib/apt/lists/*

# 2. JAR aus Build Stage kopieren
COPY --from=build /app/target/*.jar app.jar

# 3. Das Start-Skript ohne fehlerhafte Backslashes
# Wir nutzen einfache Anführungszeichen für den DB-Namen
RUN echo '#!/bin/bash \n\
pg_ctlcluster 18 main start \n\
until pg_isready; do sleep 1; done \n\
su - postgres -c "psql -c \"CREATE DATABASE \\\"schoolInventoryDS\\\";\"" \n\
su - postgres -c "psql -c \"ALTER USER postgres WITH PASSWORD '\''postgres'\'';\"" \n\
java -jar /app/app.jar' > /app/start.sh && chmod +x /app/start.sh

# 4. Verbindungseinstellungen (lokal im Container)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/schoolInventoryDS
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres

EXPOSE 8080
ENTRYPOINT ["/app/start.sh"]
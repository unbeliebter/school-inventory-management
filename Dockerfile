FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN apt-get update && apt-get install -y wget gnupg2 lsb-release && \
    wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add - && \
    echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list && \
    apt-get update && \
    apt-get install -y postgresql-18 && \
    rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/*.jar app.jar

COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

COPY testdata.sql /app/testdata.sql

RUN chown -R postgres:postgres /var/lib/postgresql /var/run/postgresql

# 6. Umgebungsvariablen für Spring Boot
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/schoolinventoryds
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres
ENV SPRING_LIQUIBASE_CHANGE_LOG=classpath:/db/changelog/db.changelog-master.xml

EXPOSE 8080
ENTRYPOINT ["/app/entrypoint.sh"]
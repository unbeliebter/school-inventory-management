#!/bin/bash
set -e

pg_ctlcluster 18 main start

until pg_isready; do
  echo "Warte auf Postgres..."
  sleep 1
done

exists=$(su - postgres -c "psql -tAc \"SELECT 1 FROM pg_database WHERE datname='schoolinventoryds'\"")

if [ "$exists" != "1" ]; then
  su - postgres -c "psql -c \"CREATE DATABASE schoolinventoryds;\""
  su - postgres -c "psql -c \"ALTER USER postgres WITH PASSWORD 'postgres';\""
fi

# Spring Boot im Hintergrund starten
java -jar /app/app.jar &
APP_PID=$!

# Warten bis Liquibase fertig ist (Tabellen existieren)
echo "Warte auf Spring Boot und Liquibase..."
for i in $(seq 1 60); do
  TABLE_COUNT=$(su - postgres -c "psql -d schoolinventoryds -tAc \"SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='public'\"" 2>/dev/null || echo "0")
  if [ "$TABLE_COUNT" -gt "0" ]; then
    echo "Schema bereit ($TABLE_COUNT Tabellen gefunden)."
    break
  fi
  echo "Warte... ($i/60)"
  sleep 3
done

# Testdaten einspielen
echo "Lade Testdaten..."
su - postgres -c "psql -d schoolinventoryds -f /app/testdata.sql"
echo "Testdaten erfolgreich geladen."

# Vordergrund halten
wait $APP_PID
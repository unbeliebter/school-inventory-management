#!/bin/bash
set -e

# Postgres starten
pg_ctlcluster 18 main start

until pg_isready; do
  echo "Warte auf Postgres..."
  sleep 1
done

# Datenbank anlegen, falls nicht vorhanden
exists=$(su - postgres -c "psql -tAc \"SELECT 1 FROM pg_database WHERE datname='schoolinventoryds'\"")

if [ "$exists" != "1" ]; then
  su - postgres -c "psql -c \"CREATE DATABASE schoolinventoryds;\""
  su - postgres -c "psql -c \"ALTER USER postgres WITH PASSWORD 'postgres';\""
fi

# Spring Boot im Hintergrund starten
java -jar /app/app.jar &
APP_PID=$!

echo "Warte auf Spring Boot und Liquibase-Abschluss..."

# Warteschleife für Liquibase-Status
for i in $(seq 1 60); do
  # 1. Prüfen, ob die Sperrtabelle existiert und entsperrt ist (locked = 'f')
  LOCKED_STATUS=$(su - postgres -c "psql -d schoolinventoryds -tAc \"SELECT locked FROM databasechangeloglock WHERE id=1\"" 2>/dev/null || echo "t")

  # 2. Prüfen, ob schon Einträge im Log vorhanden sind (Migration hat stattgefunden)
  LOG_COUNT=$(su - postgres -c "psql -d schoolinventoryds -tAc \"SELECT count(*) FROM databasechangelog\"" 2>/dev/null || echo "0")

  if [ "$LOCKED_STATUS" = "f" ] && [ "$LOG_COUNT" -gt 0 ]; then
    echo "Liquibase Migration erfolgreich beendet ($LOG_COUNT Changesets angewendet)."
    break
  fi

  if [ "$i" -eq 60 ]; then
    echo "Timeout: Liquibase wurde nicht rechtzeitig fertig oder ist noch gesperrt."
    # Optional: kill $APP_PID
    exit 1
  fi

  echo "Warte auf Schema-Ready... ($i/60) - Aktuelle Changesets: $LOG_COUNT"
  sleep 3
done

# Testdaten einspielen (jetzt sind die Tabellen garantiert da)
echo "Lade Testdaten..."
su - postgres -c "psql -d schoolinventoryds -f /app/testdata.sql"
echo "Testdaten erfolgreich geladen."

# App im Vordergrund halten
wait $APP_PID
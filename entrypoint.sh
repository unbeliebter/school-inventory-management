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

java -jar /app/app.jar &
APP_PID=$!

echo "Warte auf Spring Boot..."
until curl -s http://localhost:8080/actuator/health | grep -q '"status":"UP"'; do
  sleep 2
done

echo "Lade Testdaten..."
su - postgres -c "psql -d schoolinventoryds -f /app/testdata.sql"
echo "Testdaten erfolgreich geladen."

wait $APP_PID
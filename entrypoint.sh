#!/bin/bash
set -e

pg_ctlcluster 18 main start

until pg_isready; do
  echo "Warte auf Postgres..."
  sleep 1
done

exists=$(su - postgres -c "psql -tAc \"SELECT 1 FROM pg_database WHERE datname='schoolInventoryDS'\"")

if [ "$exists" != "1" ]; then
  su - postgres -c "psql -c \"CREATE DATABASE \\\"schoolInventoryDS\\\";\""
  su - postgres -c "psql -c \"ALTER USER postgres WITH PASSWORD 'postgres';\""
fi

exec java -jar /app/app.jar
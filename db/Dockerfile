FROM postgres:15.3-alpine

COPY ./db/init-scripts/create-tables.sql /docker-entrypoint-initdb.d/

EXPOSE 5432

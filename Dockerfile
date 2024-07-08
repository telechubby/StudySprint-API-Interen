# Use the official PostgreSQL image from Docker Hub
FROM postgres:latest

# Environment variables
ENV POSTGRES_DB postgresDB
ENV POSTGRES_USER admin
ENV POSTGRES_PASSWORD StudySpr1nt

# Expose the default PostgreSQL port
EXPOSE 5432

# Copy the SQL scripts (if needed) or configuration files
# COPY init.sql /docker-entrypoint-initdb.d/

# Start the PostgreSQL server
CMD ["postgres"]

FROM eclipse-temurin:17-jre-jammy

LABEL description="Petitions System - Spring Boot Application"
LABEL version="1.0.0"

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY target/alexpetitions-*.war app.war

ENV JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/ || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.war"]
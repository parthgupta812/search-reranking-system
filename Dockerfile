FROM openjdk:21-jdk-slim

LABEL maintainer="Nirmit Goyal <nirmitgoyal.goyal@gmail.com>"
LABEL description="Search Re-Ranking System using Java 21 and Spring Boot"

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Make Maven wrapper executable
RUN chmod +x mvnw

# Download dependencies (for layer caching)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src/ src/

# Build application
RUN ./mvnw clean package -DskipTests

# Create runtime image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy built JAR
COPY --from=0 /app/target/search-reranking-system-*.jar app.jar

# Create non-root user
RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chown -R appuser:appuser /app
USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/api/search/health || exit 1

# Run application
ENTRYPOINT ["java", "--enable-preview", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
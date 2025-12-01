# MaterialDelivery

A small Spring Boot service that records material deliveries for an asphalt plant.

This repository provides a REST endpoint to receive material deliveries, store them in a PostgreSQL database, and exposes Actuator health endpoints.

**Quick facts**
- **Language:** Java
- **Framework:** Spring Boot 3.3.x
- **Java version:** 17
- **Persistence:** Spring Data JPA + PostgreSQL
- **Migrations:** Flyway

**Contents**
- `src/main/java` - application source
- `src/main/resources` - configuration and Flyway migrations
- `docker-compose.yml` - helper services (Kafka/Zookeeper/Postgres)
 - `src/main/java/com/example/demo/kafka` - Kafka producer/consumer and event types

**Prerequisites**
- Java 17 (or compatible JDK)
- Maven (or use the included wrapper `./mvnw`)
- Docker & Docker Compose (to run Postgres/Kafka if needed)

**Build & Run**

Run with Maven (development):

```bash
./mvnw spring-boot:run
```

Build the jar:

```bash
./mvnw -DskipTests package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

If you prefer to run dependencies via Docker Compose, start them first:

```bash
docker-compose up -d
# waits a few seconds for postgres to be ready
```

The application reads DB settings from the active Spring profile. A local example is provided in `src/main/resources/application-local.yml`.

**Configuration**

- Plant-specific properties are under the `plant` prefix (bound to `PlantConfig`):
  - `plant.maxTonnagePerHour` (int)
  - `plant.plantName` (string)
  - `plant.maintenanceMode` (boolean)

- Example DB config is in `application-local.yml`.

**HTTP API**

Base path: `/deliveries`

- POST `/deliveries/material` — accept a material delivery request.

Request JSON (example):

```json
{
  "materialId": "M-123",
  "tons": 120,
  "deliveryTime": "2025-11-30T14:30:00"
}
```

Validation rules:
- `materialId` must be non-empty
- `tons` must be positive and not exceed `plant.maxTonnagePerHour`
- `deliveryTime` must be present (ISO-8601 datetime)

Behavior:
- If `plant.maintenanceMode` is true the endpoint returns `503 Service Unavailable`.
- On success the service records the delivery and returns HTTP `202 Accepted`.

Data model (entity `MaterialDelivery`):
- `id` (generated Long)
- `materialId` (String)
- `tons` (int)
- `deliveryTime` (LocalDateTime)
- `plantName` (String)

Actuator endpoints (available when actuator is enabled):
- `/actuator/health`
- `/actuator/metrics`

**Kafka integration**

- The project includes a small Kafka integration under `src/main/java/com/example/demo/kafka`:
  - `MaterialDeliveredEvent.java` — immutable record that models the event payload sent between services.
  - `MaterialDeliveryEventProducer.java` — a simple producer that uses `KafkaTemplate` to send events to the `material-events` topic.
  - `MaterialEventConsumer.java` — a consumer that listens on `material-events` (and `material-events.dlt`) and forwards processed events to the `DeliveryService`.

- Topics used by the app:
  - `material-events`
  - `material-events.dlt` (dead-letter topic)

- Spring Kafka is used; set `spring.kafka.bootstrap-servers` and any listener/container factory properties in your active profile when running locally.

**Database migrations**

Flyway migrations are stored under `src/main/resources/db/migration`. The first migration `V1__initial_schema.sql` creates the `material_delivery` table.

**Docker Compose**

`docker-compose.yml` in the repo brings up helper services used during development: `zookeeper`, `kafka`, and `postgres`.

Start:

```bash
docker-compose up -d
```

Stop & remove:

```bash
docker-compose down
```

Note: the application itself is not packaged as a container in this repo by default — you can build a container image and add a service to the compose file if desired.

**Common commands**

- Build: `./mvnw -DskipTests package`
- Run tests: `./mvnw test`
- Run app: `./mvnw spring-boot:run` or `java -jar target/demo-0.0.1-SNAPSHOT.jar`

**Contributing**

1. Fork the repo
2. Create a feature branch
3. Run tests locally
4. Submit a PR with a clear description of changes

**License**

This repository has no license file. Add a `LICENSE` if you want to make the code open source.

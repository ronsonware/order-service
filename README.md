# Order Service

## Overview
Order Service is a microservice designed to handle the creation, listing, and management of orders in a resilient and scalable way. The project is built using modern technologies to ensure reliability, maintainability, and extensibility.

## Technologies Used
- **Java 21**: Modern Java features for a robust application.
- **Spring Boot 3**: Simplifies development with Spring's ecosystem.
- **Apache Kafka**: Message broker for resilient event-driven communication.
- **PostgreSQL**: Relational database for persistent storage.
- **Mockito & JUnit 5**: Unit testing framework with AAA (Arrange-Act-Assert) methodology.

## Prerequisites
- Docker
- Makefile support
- Java 21 or later

## Running Locally
The project includes a `Makefile` for managing the infrastructure locally. Use the following commands:

1. **Start the infrastructure:**
   ```bash
   make infra/up
   ```

2. **Stop the infrastructure:**
   ```bash
   make infra/stop
   ```

3. **Remove the infrastructure:**
   ```bash
   make infra/down
   ```

## Kafka Examples
To test Kafka locally, use `kcat` (formerly known as kafkacat). Below are some example commands to send events to the `orders` topic:

### Example Event 1
```bash
echo '{"uuid": "8f9452e6-2f8f-4d64-b4be-5d92bf9b9a15", "status": "PENDING", "price": 95.75, "products": [{"name": "Product A", "description": "Description A", "value": 50.25}, {"name": "Product B", "description": "Description B", "value": 45.50}]}' | kcat -b localhost:9092 -P -t orders
```

### Example Event 2
```bash
echo '{"uuid": "123e4567-e89b-12d3-a456-426614174000", "status": "CANCELLED", "price": 150.00, "products": [{"name": "Product X", "description": "High-quality", "value": 150.00}]}' | kcat -b localhost:9092 -P -t orders
```

## API Usage
The service exposes a REST API to fetch orders with pagination. Below is an example using `curl`:

### Fetch Orders
```bash
curl -X GET "http://localhost:8080/orders?page=0&size=10"
```

### Swagger Documentation
The project provides Swagger documentation for all available APIs. You can access it at:
```
http://localhost:8080/swagger-ui.html
```

## Design Decisions
1. **Kafka for Resilience:**
    - Orders are consumed from Kafka topics to ensure reliable message delivery and processing.
    - Includes the potential for handling errors by retrying three times and forwarding failed messages to a Dead Letter Queue (DLL).

2. **PostgreSQL for Persistence:**
    - Chosen for its robust relational capabilities and support for ACID transactions.
    - Future improvements could include a PostgreSQL cluster for enhanced scalability and availability, particularly for integration with external services like Service B.

3. **Unit Testing with AAA Pattern:**
    - All unit tests follow the Arrange-Act-Assert pattern for clarity and maintainability.

## Future Improvements
- Implement retries for Kafka message consumption (up to 3 attempts) with fallback to a Dead Letter Queue (DLL).
- Deploy a PostgreSQL cluster to improve read performance for high-demand scenarios.
- Enhance resilience and scalability for external service integrations.
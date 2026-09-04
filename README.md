# Order Service

A Quarkus-based Order Service for creating orders, converting USD amounts into a target currency, storing orders in PostgreSQL, publishing `ORDER_CREATED` events to Kafka, and indexing events into OpenSearch.

## Architecture

Client -> Quarkus REST API -> Exchange Rate API
                         -> PostgreSQL
                         -> Kafka -> Kafka Consumer -> OpenSearch -> OpenSearch Dashboard

## Technology Stack

- Java 21
- Quarkus
- Maven
- PostgreSQL 16
- Apache Kafka 4.0
- OpenSearch 2.19.1
- OpenSearch Dashboards 2.19.1
- Docker and Docker Compose
- Git and GitHub

## API

### Create Order

Method: `POST`

Endpoint: `/api/v1/orders`

Request:

    {
      "customerId": "CUST-1001",
      "amountUSD": 150.00,
      "targetCurrency": "EUR"
    }

Response:

    {
      "orderId": 1,
      "customerId": "CUST-1001",
      "amountUSD": 150.00,
      "targetCurrency": "EUR",
      "convertedAmount": 129.50,
      "status": "PROCESSED",
      "createdAt": "2026-09-03T12:21:39.682610948Z"
    }

## Exchange Rate API

The service uses the live Exchange Rate API:

`https://open.er-api.com/v6/latest/USD`

The requested target currency rate is retrieved from the USD response and the converted amount is rounded to two decimal places.

## PostgreSQL

Orders are stored in the `orders` table.

- Database: `projectForm`
- Username: `postgres`

When running with Docker Compose, the application connects to PostgreSQL using `postgres:5432`.

## Kafka

After an order is successfully created, an `ORDER_CREATED` event is published to the `orders` Kafka topic.

The Kafka consumer receives the event and indexes the order into OpenSearch.

## OpenSearch

Order events are indexed into the `orders` index.

OpenSearch URL: `http://localhost:9200`

Indexed fields:

- `orderId`
- `customerId`
- `amountUSD`
- `targetCurrency`
- `convertedAmount`
- `status`
- `createdAt`

## OpenSearch Dashboard

Dashboard URL: `http://localhost:5601`

The analytics dashboard contains:

1. Total Gross Revenue - `SUM(amountUSD)`
2. Revenue Over Time - `createdAt` date histogram vs `SUM(amountUSD)`
3. Revenue by Currency - terms of `targetCurrency` vs `SUM(amountUSD)`
4. Top 5 Customers by Revenue - `customerId` vs `SUM(amountUSD)`

## Running with Maven

Build:

    ./mvnw clean package

Run tests:

    ./mvnw test

Run in development mode:

    ./mvnw quarkus:dev

The Maven application runs on `http://localhost:8080`.

## Running with Docker Compose

Build the application:

    ./mvnw clean package -DskipTests

Build the Docker image:

    docker build -t order-service:1.0 .

Start all services:

    docker compose up -d

Check services:

    docker compose ps

Stop services:

    docker compose down

## Docker Ports

- Order Service: `8081`
- Kafka: `9092`
- OpenSearch: `9200`
- OpenSearch Dashboards: `5601`
- PostgreSQL: `5432` internal to Docker network

Docker Order Service URL:

`http://localhost:8081`

## API Testing

Example:

    curl -i -X POST http://localhost:8081/api/v1/orders \
    -H "Content-Type: application/json" \
    -d '{
      "customerId": "CUST-1001",
      "amountUSD": 150.00,
      "targetCurrency": "EUR"
    }'

Successful request returns HTTP `201 Created`.

## Error Handling

- `201 Created` - order created successfully
- `400 Bad Request` - invalid or unsupported request
- `500 Internal Server Error` - unexpected server-side error

## Project Structure

    order-service/
    ├── Dockerfile
    ├── docker-compose.yml
    ├── pom.xml
    ├── README.md
    └── src/
        ├── main/
        │   ├── java/com/adee/
        │   │   ├── OrderResource.java
        │   │   ├── client/
        │   │   ├── dto/
        │   │   ├── entity/
        │   │   ├── event/
        │   │   ├── kafka/
        │   │   ├── opensearch/
        │   │   └── service/
        │   └── resources/
        │       └── application.properties
        └── test/
            └── java/com/adee/
                └── OrderResourceTest.java

## GitHub

Repository:

`https://github.com/harshit1166/order-service`

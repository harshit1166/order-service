# Order Service

A Quarkus-based Order Service that creates orders, converts USD amounts into the requested target currency using a live Exchange Rate API, stores orders in PostgreSQL, publishes `ORDER_CREATED` events to Kafka, and indexes order events into OpenSearch for analytics.

## Architecture

```text
Client
  |
  | POST /api/v1/orders
  v
Quarkus Order Service
  |
  +----> Exchange Rate API
  |
  +----> PostgreSQL
  |
  +----> Kafka (orders topic)
              |
              v
        Kafka Consumer
              |
              v
          OpenSearch
              |
              v
    OpenSearch Dashboard

# LinkedIn Clone — Microservices Backend

A production-style LinkedIn backend built with Spring Boot microservices, Apache Kafka, Neo4j, and Spring Cloud. Simulates core LinkedIn features including user connections, post feeds, and real-time notifications using an event-driven architecture.

---

## Architecture Overview

```
Client Request
      │
      ▼
 API Gateway (Spring Cloud Gateway + JWT Auth)
      │
      ├──▶ userService        → User profiles, authentication (JWT + Spring Security)
      ├──▶ postsService       → Create/fetch posts, triggers Kafka events
      ├──▶ connectionService  → Send/accept connections, graph-based discovery
      └──▶ notificationService → Consumes Kafka events, delivers notifications

Discovery: Eureka Server (service registration & lb:// routing)
```

---

## Services

| Service | Responsibility |
|---|---|
| **APIGateway** | Single entry point, JWT authentication filter, routes to services via Eureka |
| **DiscoveryServer** | Eureka service registry for dynamic service discovery |
| **userService** | User registration, login, JWT token generation, Spring Security + RBAC |
| **postsService** | Post CRUD, publishes Kafka events on new post creation |
| **connectionService** | Connection requests, Neo4j graph modeling for 1st/2nd degree discovery |
| **notificationService** | Kafka consumer, processes events from posts and connections |

---

## Tech Stack

- **Java 17** + **Spring Boot 3.x**
- **Spring Cloud Gateway** — API gateway with JWT filter
- **Eureka** — Service discovery and load balancing (`lb://`)
- **Apache Kafka** — Event-driven notifications (producer/consumer)
- **Neo4j** — Graph database for connection modeling (`@Relationship`, Cypher queries)
- **Spring Security** — JWT-based authentication and role-based access control
- **Feign Client** — Inter-service HTTP calls with `RequestInterceptor` for header propagation
- **Spring Data JPA** — Relational data persistence
- **Lombok** — Boilerplate reduction
- **Maven** — Build tool

---

## Key Features

- **JWT Authentication** — Stateless auth via Spring Security; token validated at the gateway before forwarding to services
- **Graph-based Connection Discovery** — Neo4j models user connections as graph nodes/edges; Cypher queries support 1st and 2nd degree connection suggestions (similar to LinkedIn's "People You May Know")
- **Event-Driven Notifications** — postsService and connectionService publish Kafka events; notificationService consumes them asynchronously, decoupling notification logic from core business logic
- **Cross-Service User Context** — `Y-USER-ID` header propagated across services using Feign `RequestInterceptor`
- **Centralized Routing** — All traffic goes through the API Gateway; services register with Eureka and are called via `lb://serviceName`

---

## Running Locally

### Prerequisites
- Java 17+
- Maven
- Neo4j (local or Neo4j AuraDB free tier)
- Apache Kafka + Zookeeper (or KRaft mode)
- MySQL / PostgreSQL

### Steps

```bash
# 1. Start Eureka Discovery Server
cd DiscoveryServer
mvn spring-boot:run

# 2. Start API Gateway
cd APIGateway
mvn spring-boot:run

# 3. Start each microservice (in any order)
cd userService/userService && mvn spring-boot:run
cd postsService/postsService && mvn spring-boot:run
cd connectionService/connectionService && mvn spring-boot:run
cd notificationService && mvn spring-boot:run
```

### Default Ports

| Service | Port |
|---|---|
| DiscoveryServer (Eureka) | 8761 |
| APIGateway | 8080 |
| userService | 8081 |
| postsService | 8082 |
| connectionService | 8083 |
| notificationService | 8084 |

---

## API Endpoints (via Gateway)

### Auth
```
POST /auth/register     → Register new user
POST /auth/login        → Login, returns JWT token
```

### Posts
```
POST   /posts           → Create a post (auth required)
GET    /posts           → Get feed
```

### Connections
```
POST   /connections/request/{userId}   → Send connection request
POST   /connections/accept/{userId}    → Accept connection
GET    /connections/suggestions        → Get 2nd degree suggestions
```

---

## Project Highlights

- Resolved Kafka `ClassNotFoundException` across services by standardizing event contracts and configuring `ErrorHandlingDeserializer`
- Debugged Spring Cloud Gateway dependency conflicts (`spring-cloud-starter-gateway-server-webflux`) and YAML routing issues
- Implemented proper `ThreadLocal` cleanup in `HandlerInterceptor` to avoid memory leaks in request-scoped handling

---

## Author

**Surjya Narale**
[LinkedIn](linkedin.com/in/suraj-narale) • [GitHub](github.com/surajNarale4)

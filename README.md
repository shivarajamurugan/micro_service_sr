# TEK Microservices Project

A Spring Boot + Spring Cloud microservices demo consisting of a Eureka service registry, an API Gateway, and two business services (User Service, Order Service) that communicate over REST via OpenFeign, protected with Resilience4j circuit breaker and retry.

## Architecture

```
                        ┌─────────────────────┐
                        │  Eureka Server       │
                        │  (Service Registry)  │
                        │  port: 8761          │
                        └─────────▲────────────┘
                                  │ register/discover
              ┌───────────────────┼───────────────────┐
              │                   │                   │
   ┌──────────┴────────┐  ┌───────┴────────┐  ┌───────┴────────┐
   │  API Gateway       │  │  User Service  │  │  Order Service │
   │  port: 8085        │  │  port: 8334    │  │  port: 8333    │
   └────────────────────┘  └────────┬───────┘  └───────┬────────┘
                                     │   Feign call      │
                                     │◄───────────────────┘
                                     │  (Circuit Breaker + Retry)
                              ┌──────┴───────┐    ┌──────────────┐
                              │  MySQL        │    │  MySQL       │
                              │  user db      │    │  order db    │
                              └───────────────┘    └──────────────┘
```

## Services

| Service | Port | Responsibility |
|---|---|---|
| **TEK_Serivice_Registry** | 8761 | Eureka service discovery server |
| **TEK_API_Gateway** | 8085 | Single entry point / reverse proxy (Spring Cloud Gateway) |
| **TEK_UserService** | 8334 | User CRUD, backed by MySQL |
| **TEK_OrderService** | 8333 | Order CRUD, calls User Service to validate users |

## Tech Stack

- **Java / Spring Boot 4.1.1** (Gateway on Spring Boot 3.2.5)
- **Spring Cloud 2025.1.3** (Gateway on 2023.0.1)
- **Spring Cloud Netflix Eureka** — service discovery
- **Spring Cloud OpenFeign** — declarative REST client
- **Resilience4j** — circuit breaker + retry
- **Spring Data JPA / Hibernate** — persistence
- **MySQL** — database per service
- **Lombok** — boilerplate reduction (`@Builder`, `@Getter/@Setter`, `@RequiredArgsConstructor`)
- **springdoc-openapi** — Swagger/OpenAPI docs (Order Service)

## Prerequisites

- JDK 17+ (matching your Spring Boot 4 requirement)
- Maven
- MySQL running locally on port 3306

## Running Locally

Start services in this order:

1. **Service Registry**
   ```bash
   cd TEK_Serivice_Registry
   mvn spring-boot:run
   ```
   Eureka dashboard: http://localhost:8761

2. **User Service**
   ```bash
   cd TEK_UserService
   mvn spring-boot:run
   ```
   Creates database `tek_micro_user_service` automatically.

3. **Order Service**
   ```bash
   cd TEK_OrderService
   mvn spring-boot:run
   ```
   Creates database `tek_micro_order_service` automatically.

4. **API Gateway**
   ```bash
   cd TEK_API_Gateway
   mvn spring-boot:run
   ```

> ⚠️ Update the MySQL `username`/`password` in each service's `src/main/resources/application.yml` to match your local setup.

## API Endpoints

### User Service (`/api/user`)
| Method | Path | Description |
|---|---|---|
| POST | `/api/user` | Create a user |
| GET | `/api/user?email=` | Get all users (paginated, optional email filter) |
| GET | `/api/user/{userId}` | Get user by ID |

### Order Service (`/api/order`)
| Method | Path | Description |
|---|---|---|
| POST | `/api/order` | Create an order (validates user via User Service) |
| GET | `/api/order?userId=&orderId=` | Get orders (paginated, optional filters) |

## Resilience Configuration

Order Service calls User Service through a Feign client wrapped with:
- **Circuit Breaker**: sliding window of 10 calls, 50% failure threshold, 10s open-state wait, 5 permitted calls in half-open state
- **Retry**: up to 3 attempts, 200ms wait between attempts
- **Fallback**: returns `null` and logs a message when the User Service is unavailable

## Project Structure (per service)

```
src/main/java/com/<Service>/
├── controller/     # REST controllers
├── service/        # Service interfaces + Impl
├── repository/     # Spring Data JPA repositories
├── entity/         # JPA entities (+ enums)
├── dto/            # Request/response DTOs
└── client/         # Feign clients (Order Service only)
```

## Known Gaps / Next Steps

- API Gateway has no explicit route definitions yet — only registers with Eureka.
- Bean validation (`@Valid`, `@NotNull`, etc.) dependency is included in Order Service but not yet applied to DTOs.
- Actuator dependency is included in Order Service but endpoints aren't customized/exposed.
- No centralized exception handling (`@ControllerAdvice`) across services.
- No API documentation UI wired up beyond the springdoc dependency in Order Service.

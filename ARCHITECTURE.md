# NexusMarket — Software Architecture

## Overview

NexusMarket follows a Hexagonal Architecture (Ports and Adapters) combined
with Domain-Driven Design (DDD) principles.

The primary objective of this architecture is to isolate the business
domain from external technologies, ensuring that business rules remain
independent from frameworks, databases, communication protocols, and
infrastructure concerns.

This approach promotes maintainability, scalability, testability, and
technology independence.

## Architectural Principles

The architecture is based on the following principles:

- Domain-first design.
- Separation of concerns.
- Dependency inversion.
- Technology independence.
- High cohesion.
- Low coupling.
- Explicit boundaries between layers.

The domain contains all business rules and never depends on external
technologies.

## Architecture Layers

The application is organized into four major components:

```
Application
│
├── Adapters
│
├── Domain
│
└── Infrastructure
```

Each component has a clearly defined responsibility.

## Package Structure

```
src/
└── main/
    └── java/
        └── application/
            │
            ├── DemoApplication.java
            │
            ├── adapters/
            │   │
            │   ├── in/
            │   │   └── rest/
            │   │       ├── controllers/
            │   │       ├── requests/
            │   │       ├── responses/
            │   │       └── mappers/
            │   │
            │   └── out/
            │       └── persistence/
            │           └── postgres/
            │               ├── adapters/
            │               ├── entities/
            │               ├── repositories/
            │               └── mappers/
            │
            ├── domain/
            │   ├── models/            ← IMPLEMENTED (this stage)
            │   ├── valueobjects/      ← IMPLEMENTED (this stage)
            │   ├── enums/             ← IMPLEMENTED (this stage)
            │   ├── services/
            │   ├── exceptions/
            │   └── ports/
            │       ├── in/
            │       └── out/
            │
            └── infrastructure/
                ├── config/
                ├── database/
                └── security/
```

## Layer Responsibilities

### Application

The `application` package represents the root of the project. It contains
the application entry point and all architectural components.

**Responsibilities**
- Application bootstrap.
- Component organization.
- Dependency composition.

#### `DemoApplication.java`

**Responsibilities**
- Initialize the application.
- Load the infrastructure.
- Configure dependency injection.
- Start the REST server.

### Adapters

The adapters connect external technologies with the business domain.
Adapters translate external requests into domain operations and transform
domain objects into technology-specific representations. The domain never
communicates directly with external systems.

#### Input Adapters — `adapters/in/rest`

**Responsibilities**
- Receive HTTP requests.
- Validate incoming data.
- Convert Request DTOs into Domain Models.
- Execute application use cases.
- Convert domain results into Response DTOs.

**Controllers** expose REST endpoints, delegate execution to the domain,
and return HTTP responses. Controllers must never implement business rules.

**Requests** are DTOs representing incoming HTTP payloads. They receive
client data, validate input, and transport it into the application. They
must not contain business logic.

**Responses** are DTOs representing outgoing HTTP responses. They return
processed information, hide internal domain implementation, and
standardize API responses.

**Mappers** convert between Request DTO ↔ Domain Model and Domain Model ↔
Response DTO, preventing the domain from depending on transport objects.

#### Output Adapters — `adapters/out/persistence/postgres`

Output adapters connect the domain with external resources — in this
project's current scope, a single relational database (PostgreSQL).

**Entities** represent relational database tables (JPA `@Entity` classes),
kept separate from domain `models` so the domain never depends on JPA.

**Repositories** implement persistence operations (Spring Data JPA
interfaces).

**Mappers** convert Domain Models into database entities and back.

**Adapters** implement the domain's Output Ports, wiring the JPA
repositories to what the domain actually needs.

### Domain

The Domain layer is the core of the application. It contains all business
rules and must remain independent from any external technology.

No class inside the domain may depend on: Spring, JPA, HTTP, REST, JSON, or
SQL.

#### Models (`domain/models`) — **implemented**

Contain the business entities: `User`, `Buyer`, `Seller`, `Administrator`,
`Warehouse`, `MarketplaceWarehouse`, `SellerWarehouse`, `Product`,
`PhysicalProduct`, `DigitalProduct`, `Inventory`, `InventoryMovement`,
`Cart`, `CartItem`, `Order`, `OrderItem`, `Invoice`, `Return`, `Refund`.
These objects represent the marketplace business.

#### Value Objects (`domain/valueobjects`) — **implemented**

Represent fixed lifecycle states tied to a single entity: `UserStatus`,
`RoleType`, `ProductStatus`, `MovementType`, `CartStatus`, `OrderStatus`,
`RefundStatus`. Value Objects are compared by value instead of identity.

#### Enums (`domain/enums`) — **implemented**

Contain classifications representing the outcome of a business decision,
rather than an ongoing lifecycle state: `ReturnDecision`.

#### Services (`domain/services`) — *planned*

Will contain business logic that does not naturally belong to a single
entity. Examples: `OrderCheckoutService`, `RefundApprovalService`,
`InventoryReservationService`. Services coordinate business operations
while preserving domain integrity.

#### Ports (`domain/ports`) — *planned*

Ports define communication contracts between the domain and external
technologies. The domain owns all interfaces.

**Input Ports** (`ports/in`) represent application use cases, e.g.
`CreateOrderUseCase`, `RequestReturnUseCase`, `ApproveRefundUseCase`.

**Output Ports** (`ports/out`) represent dependencies required by the
domain, e.g. `UserRepositoryPort`, `InventoryRepositoryPort`,
`NotificationPort`.

#### Exceptions (`domain/exceptions`) — *planned*

Will contain business exceptions such as `InsufficientStockException`,
`ReturnNotApprovedException`, `InvalidOrderStateException`. Business
exceptions belong exclusively to the domain.

### Infrastructure

Infrastructure contains technical configuration required by the
application. It does not contain business logic.

**Config** — application configuration (REST configuration,
serialization, environment configuration).

**Database** — database initialization and connection configuration
(PostgreSQL configuration, connection pools).

**Security** — authentication and authorization configuration (if
required in a later stage).

## Dependency Flow

Dependencies always point toward the domain.

```
REST Controller
        │
        ▼
Input Port
        │
        ▼
Domain Service
        │
        ▼
Output Port
        │
        ▼
Persistence Adapter
        │
        ▼
Database
```

The domain never depends on adapters or infrastructure.

## Benefits

This architecture provides:

- Technology independence.
- High maintainability.
- Clear separation of concerns.
- Improved testability.
- Easier scalability.
- Better support for Domain-Driven Design.
- Easy replacement of frameworks or databases.
- Reusable business logic.
- Long-term maintainability.

## Architectural Constraints

The following rules must always be respected:

- Business logic belongs exclusively to the Domain layer.
- Controllers must not contain business rules.
- DTOs must never enter the Domain layer.
- Persistence entities must never be exposed through the API.
- Communication between technologies and the Domain must occur only
  through Ports.
- Adapters implement Ports but never define business rules.
- Infrastructure depends on the Domain, never the opposite.
- Every dependency must point toward the Domain.
- Business entities must remain framework-independent.
- The Domain must be fully testable without requiring infrastructure
  components.

## Current Implementation Status

As of this stage, only `domain/models`, `domain/valueobjects`, and
`domain/enums` are implemented, per this stage's scope (modeling the
domain only). All other packages listed above (`adapters`, `services`,
`ports`, `exceptions`, `infrastructure`) are documented here for context
and will be implemented in upcoming stages.

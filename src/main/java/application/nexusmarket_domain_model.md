# NexusMarket — Domain Model Documentation

## 1. Overview

This document describes the domain model implemented for the NexusMarket
marketplace platform, developed for the *Construcción de Software 2* course
project. It covers the domain entities, value objects (enums), and decision
enums, along with the reasoning behind each design decision.

The model follows the same organizational pattern used in the reference
example provided for this stage: a `domain` package holding the core
entities, with two dedicated sub-packages — `valueobjects` for fixed-state
attributes tied to an entity's lifecycle, and `enums` for decision/outcome
classifications.

## 2. Package Structure

```
application/
  domain/
    User.java, Buyer.java, Seller.java, Administrator.java
    Warehouse.java, MarketplaceWarehouse.java, SellerWarehouse.java
    Product.java, PhysicalProduct.java, DigitalProduct.java
    Inventory.java, InventoryMovement.java
    Cart.java, CartItem.java
    Order.java, OrderItem.java
    Invoice.java
    Return.java
    Refund.java
    valueobjects/
      UserStatus.java, RoleType.java
      ProductStatus.java
      MovementType.java
      CartStatus.java
      OrderStatus.java
      RefundStatus.java
    enums/
      ReturnDecision.java
```

## 3. Domain Classes

### 3.1 Users domain

| Class | Type | Attributes | Purpose |
|---|---|---|---|
| `User` | Abstract | `userId`, `fullName`, `documentNumber`, `email`, `status` | Base identity shared by every participant. Abstract because no user in the real system is ever generic — every user is a Buyer, Seller, or Administrator. |
| `Buyer` | Concrete (extends `User`) | `shippingAddress`, `additionalAddresses`, `commercialStatus` | Represents a customer who purchases products. Holds delivery-related data no other user type needs. |
| `Seller` | Concrete (extends `User`) | `sellerCode` | Represents a third-party vendor who publishes products and owns warehouse stock. |
| `Administrator` | Concrete (extends `User`) | `adminLevel`, `roleType` | Represents internal staff. `roleType` (see `RoleType`) allows a single Administrator to also act as a Supervisor or Logistics Operator, avoiding unnecessary subclasses for roles that only consult or execute operations on other domains without owning their own data. |

### 3.2 Warehouses domain

| Class | Type | Attributes | Purpose |
|---|---|---|---|
| `Warehouse` | Abstract | `warehouseId`, `name`, `location`, `capacity` | Base structure shared by both kinds of physical storage. |
| `MarketplaceWarehouse` | Concrete (extends `Warehouse`) | `operatingStatus` | Storage facility operated directly by the platform. |
| `SellerWarehouse` | Concrete (extends `Warehouse`) | `warehouseStatus` | Storage facility operated by an individual seller. |

### 3.3 Catalog domain

| Class | Type | Attributes | Purpose |
|---|---|---|---|
| `Product` | Abstract | `productId`, `name`, `description`, `price`, `status` | Base structure shared by every product type. |
| `PhysicalProduct` | Concrete (extends `Product`) | `weight`, `dimensions`, `shippingType` | Products requiring inventory tracking and shipment. |
| `DigitalProduct` | Concrete (extends `Product`) | `fileFormat`, `fileSize`, `downloadUrl` | Products delivered immediately after payment; no inventory or shipment involved. |

### 3.4 Inventory domain

| Class | Attributes | Purpose |
|---|---|---|
| `Inventory` | `inventoryId`, `product`, `warehouse`, `quantity`, `reservedQuantity`, `lastUpdated` | Bridges a specific `Product` with a specific `Warehouse`, tracking current and reserved stock. A single product can have separate `Inventory` records across multiple warehouses. |
| `InventoryMovement` | `movementId`, `inventory`, `type`, `quantity`, `date`, `reason` | Historical log of every stock change (see `MovementType`), providing full traceability as required by the specification. |

### 3.5 Cart domain

| Class | Attributes | Purpose |
|---|---|---|
| `Cart` | `cartId`, `buyer`, `createdDate`, `status` | A buyer's mutable pre-purchase selection. Kept separate from `Order` because its lifecycle (freely add/remove items) differs fundamentally from a confirmed order. |
| `CartItem` | `cartItemId`, `cart`, `product`, `quantity`, `unitPriceSnapshot` | Associative class resolving the many-to-many relationship between `Cart` and `Product`. Carries quantity and a price reference specific to that cart line. |

### 3.6 Orders domain

| Class | Attributes | Purpose |
|---|---|---|
| `Order` | `orderId`, `buyer`, `orderDate`, `status`, `total` | Represents a formal, immutable commercial commitment once created. Its lifecycle is tracked via `OrderStatus`. |
| `OrderItem` | `orderItemId`, `order`, `product`, `quantity`, `unitPrice`, `subtotal` | Associative class resolving the many-to-many relationship between `Order` and `Product`. Unlike `CartItem`, its `unitPrice` is a permanent historical record — it never changes even if the product's catalog price does later. |

### 3.7 Invoicing domain

| Class | Attributes | Purpose |
|---|---|---|
| `Invoice` | `invoiceId`, `order`, `issueDate`, `totalAmount`, `taxAmount`, `paymentMethod` | Financial/legal document generated once an order is paid. Kept separate from `Order` because it has its own identity and lifecycle. |

### 3.8 Returns & Refunds domain

| Class | Attributes | Purpose |
|---|---|---|
| `Return` | `returnId`, `order`, `requestDate`, `reason`, `decision` | Represents a buyer's request to return part of an order. `decision` remains `null` while pending, and is set to `APPROVED` or `REJECTED` (see `ReturnDecision`) once reviewed. |
| `Refund` | `refundId`, `relatedReturn`, `amount`, `refundDate`, `method`, `status` | Represents the financial outcome of an **approved** return. Kept separate from `Return` because a rejected return never produces a refund, and a refund has its own processing lifecycle (see `RefundStatus`). |

## 4. Value Objects (`domain/valueobjects`)

Value objects represent the fixed set of valid states an entity's attribute
can take. Each enum constant carries its description as actual data (a
`description` field with a `getDescription()` accessor), not just a code
comment, so the description is available at runtime.

| Enum | Values | Used by |
|---|---|---|
| `UserStatus` | `ACTIVE`, `INACTIVE`, `BLOCKED` | `User` |
| `RoleType` | `ADMINISTRATOR`, `SUPERVISOR`, `LOGISTICS_OPERATOR` | `Administrator` |
| `ProductStatus` | `PUBLISHED`, `SUSPENDED`, `DISCONTINUED` | `Product` |
| `MovementType` | `INCOMING`, `RESERVATION`, `SALE_OUTGOING`, `ADJUSTMENT`, `RETURN_RESTOCK` | `InventoryMovement` |
| `CartStatus` | `ACTIVE`, `CHECKED_OUT`, `ABANDONED` | `Cart` |
| `OrderStatus` | `PENDING_PAYMENT`, `PAID`, `SHIPPED`, `DELIVERED` | `Order` |
| `RefundStatus` | `PENDING`, `PROCESSED`, `FAILED` | `Refund` |

## 5. Decision Enums (`domain/enums`)

Decision enums differ from value objects in that they represent the
**outcome of a human review**, not an entity's ongoing lifecycle state.

| Enum | Values | Used by |
|---|---|---|
| `ReturnDecision` | `APPROVED`, `REJECTED` | `Return` |

## 6. Key Design Decisions

- **`Administrator` absorbs the Supervisor and Logistics Operator roles** via
  the `roleType` attribute instead of separate subclasses, because neither
  role owns persistent data of its own — they only consult or execute
  operations belonging to other domains (orders, inventory, shipments).
- **`Cart` and `Order` are separate classes**, not a single class with a
  "cart" state, because their mutability rules differ fundamentally: a cart
  can be freely edited, while a confirmed order cannot be modified.
- **`Invoice` is separate from `Order`** because it represents a distinct
  financial/legal document with its own identity, generated as a
  consequence of an order being paid.
- **`Return` and `Refund` are separate classes** because a rejected return
  never produces a refund, and a refund has its own independent processing
  lifecycle once approved.
- **All domain classes are currently plain Java objects (POJOs)** — no
  persistence annotations — per this stage's scope, which is limited to
  modeling the domain, not the persistence layer.

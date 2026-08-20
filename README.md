<div align="center">

# 🛍️ Spring Boot E-Commerce Backend

**A secure, full-featured REST API for an e-commerce platform — built from scratch with Spring Boot.**

![Java](https://img.shields.io/badge/Java-24-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Razorpay](https://img.shields.io/badge/Razorpay-Payments-0C2451?style=for-the-badge&logo=razorpay&logoColor=white)

</div>

> ⚠️ **Note:** This is a backend/portfolio project built for learning purposes and is not affiliated with any real business or trust.

---

## ✨ Features

- 🔐 **JWT Authentication** — Stateless login/register flow using Spring Security + JWT (jjwt 0.12.x)
- 🛡️ **Role-Based Access Control (RBAC)** — Separate `CUSTOMER` and `ADMIN` roles with endpoint-level authorization
- 📦 **Product & Category Management** — Full CRUD, with admin-only write access
- 🛒 **Shopping Cart** — Add, update, remove items with per-user ownership checks
- 📝 **Checkout & Orders** — Cart-to-order conversion with price snapshotting and status tracking
- 💳 **Payment Integration** — Razorpay order creation and signature verification
- 👨‍💼 **Admin Panel APIs** — View all orders, update order status, manage the catalog

---

## 🧰 Tech Stack

| Layer | Technology |
|---|---|
| 🖥️ Language | Java 24 |
| 🌱 Framework | Spring Boot 4.1.0 |
| 🔑 Security | Spring Security 7 + JWT (jjwt 0.12.7) |
| 🗄️ Database | MySQL (Spring Data JPA / Hibernate) |
| 💰 Payments | Razorpay Java SDK |
| 📦 Build Tool | Maven |
| 🧩 Utilities | Lombok |

---

## 🏗️ Architecture

Standard layered architecture:

```
Controller  →  Service  →  Repository  →  Database
```

- `entity/` — JPA entities (User, Product, Category, Cart, CartItem, Order, OrderItem, Payment)
- `repository/` — Spring Data JPA repositories
- `service/` — Business logic
- `controller/` — REST endpoints
- `dto/` — Request/response data transfer objects
- `config/` — Spring Security configuration
- `exception/` — Global exception handling

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 24+
- Maven
- MySQL 8+
- A [Razorpay](https://razorpay.com) test account (for payment features)

### ⚙️ Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/spring-boot-ecommerce-backend.git
   ```

2. **Create a MySQL database**
   ```sql
   CREATE DATABASE jayganesh_store_db;
   ```

3. **Copy the example config and fill in your own values**
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
   Update in `application.properties`:
   - Database URL, username, password
   - `jwt.secret` (a long random string) and `jwt.expiration`
   - Razorpay `key.id` and `key.secret` (from your test dashboard)

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   API available at `http://localhost:8080` 🎉

---

## 📡 API Overview

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/api/auth/register` | 🌐 Public | Register a new customer |
| `POST` | `/api/auth/login` | 🌐 Public | Login and receive JWT |
| `GET` | `/api/products` | 🌐 Public | List all products |
| `POST` | `/api/products` | 🔒 Admin | Create a product |
| `DELETE` | `/api/products/{id}` | 🔒 Admin | Delete a product |
| `GET` | `/api/categories` | 🌐 Public | List all categories |
| `POST` | `/api/categories` | 🔒 Admin | Create a category |
| `POST` | `/api/cart/add` | 👤 Authenticated | Add item to cart |
| `GET` | `/api/cart` | 👤 Authenticated | View cart |
| `PUT` | `/api/cart/update/{cartItemId}` | 👤 Owner only | Update item quantity |
| `DELETE` | `/api/cart/delete/{cartItemId}` | 👤 Owner only | Remove item |
| `POST` | `/api/orders/checkout` | 👤 Authenticated | Place an order from cart |
| `GET` | `/api/orders/all` | 🔒 Admin | View all orders |
| `PUT` | `/api/orders/status/{orderId}` | 🔒 Admin | Update order status |
| `POST` | `/api/payment/create-order` | 👤 Authenticated | Create a Razorpay order |
| `POST` | `/api/payment/verify` | 👤 Authenticated | Verify payment signature |

> All protected endpoints require an `Authorization: Bearer <token>` header.

---

## 💡 What I Learned

Building this project involved working through real integration issues — jjwt API changes across versions, Spring Security's `DaoAuthenticationProvider` constructor changes, duplicate filter registration with Spring Boot's auto-configuration, and designing secure ownership checks for user-owned resources like cart items.

---

## 🗺️ Roadmap

- [ ] React frontend
- [ ] Order history for customers
- [ ] Product search & filtering
- [ ] Email notifications on order status change

---

<div align="center">

📄 Built for educational/portfolio purposes.

</div>

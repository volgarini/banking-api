# Banking API

A modern Banking REST API built with **Java 21** and **Spring Boot 3**, following Clean Architecture principles and modern backend development best practices.

The project simulates a digital banking platform, providing secure and scalable REST APIs for account management, customer operations, and financial transactions.

This repository is part of my journey to refresh my backend engineering skills using the latest Java ecosystem and modern software architecture.

---

## 🚀 Tech Stack

- Java 21
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA
- PostgreSQL
- Docker
- Docker Compose
- Maven
- JUnit 5
- Mockito
- OpenAPI / Swagger
- GitHub Actions (CI)

---

## 📌 Features

- Customer Management
- Account Management
- Balance Inquiry
- Deposit
- Withdrawal
- Money Transfer
- Transaction History
- JWT Authentication
- Role-Based Authorization
- Exception Handling
- API Documentation

---

## 🏗 Architecture

This project follows modern backend development practices:

- Clean Architecture
- RESTful APIs
- Layered Architecture
- Dependency Injection
- SOLID Principles
- DTO Pattern
- Global Exception Handling
- Validation
- Unit Testing

---

## 📂 Project Structure

```
src
├── main
│   ├── java
│   └── resources
│       ├── application.yml
│       └── db
│           └── migration
|           └── seed
└── test
```

---

## 🔐 Authentication

Authentication will be implemented using:

- Spring Security
- JWT Tokens
- BCrypt Password Encoder
- Role-Based Access Control

---

## 🗄 Database

PostgreSQL

This project uses **Flyway** for database versioning and migration management.

Current migrations:

```text
V1__create_document_type_table.sql
V2__insert_document_types.sql
V3__create_customer_table.sql
```

Every time the application starts, Flyway automatically validates and applies pending database migrations.

---

## 🧪 Tests

The project includes:

- Unit Tests
- Service Tests
- Repository Tests
- Controller Tests

Frameworks:

- JUnit 5
- Mockito

---

## 🐳 Docker

The application can be started using Docker Compose.

```bash
docker compose up
```

---

## 📖 API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## 📈 Roadmap

- [x] Spring Boot project setup
- [x] Docker Compose
- [x] PostgreSQL
- [x] Flyway
- [x] Database schema versioning
- [x] Document Type table
- [x] Customer table
- [ ] Customer CRUD
- [ ] Account CRUD
- [ ] Deposit API
- [ ] Withdrawal API
- [ ] Transfer API
- [ ] Transaction History
- [ ] JWT Authentication
- [ ] Docker Support
- [ ] Unit Tests
- [ ] Integration Tests
- [ ] GitHub Actions
- [ ] API Documentation

---

## 🎯 Learning Goals

This project was created to explore modern Java backend development, including:

- Java 21
- Spring Boot 3
- REST APIs
- Clean Architecture
- Docker
- CI/CD
- Software Testing
- Cloud-ready applications

---

## 👨‍💻 Author

Lucas Volgarini

Senior Java Backend Developer

LinkedIn:
https://www.linkedin.com/in/lucasvolgarini/

GitHub:
https://github.com/volgarini

## 📈 Project Progress

### v0.1.0
- Initial project setup
- Spring Boot 3.5.4 configured
- Java 21 configured
- PostgreSQL configured
- Docker Compose configured

### v0.2.0
- Flyway configured
- Database versioning implemented
- Document Type table created
- Customer table created
- Initial document types seeded

## 🚀 v0.3.0

### Added

- Customer CRUD API
- Customer service layer
- Customer repository
- Customer mapper (MapStruct)
- Request and Response DTOs
- Business validation rules
- Custom exceptions
- Unit tests for CustomerService
- Unit tests for DocumentType

### Improved

- Better separation of responsibilities
- Consistent exception handling
- Cleaner service implementation
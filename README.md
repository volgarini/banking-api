# Banking API

A Banking REST API built with Java 21 and Spring Boot, following Layered Architecture and modern backend development best practices.

The project is being developed incrementally to simulate a digital banking system while applying enterprise-grade backend practices such as REST APIs, automated testing, database versioning, continuous integration, and API documentation.

This repository is part of my journey to refresh and strengthen my backend engineering skills using the modern Java ecosystem.

---

## 🚀 Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway
- Docker
- Docker Compose
- Maven
- MapStruct
- Lombok
- JUnit 5
- Mockito
- JaCoCo
- OpenAPI / Swagger
- GitHub Actions

---

## Features

### Customer

- Create Customer
- List Customers
- Find Customer by ID
- Update Customer
- Delete Customer

### Document Types

- List Document Types
- Find Document Type by ID

### Account

- Account Management
- Multiple Document Types
- Current and Savings Accounts
- Automatic Account Number Generation
- Portuguese IBAN Generation

### Infrastructure

- Flyway Database Migrations
- Global Exception Handling
- Bean Validation
- OpenAPI Documentation
- Docker Compose
- GitHub Actions CI

---

## 🏗 Architecture

## Architecture

This project follows a Layered Architecture with clear separation between:

- Controllers
- Services
- Repositories
- DTOs
- Entities
- Mappers
- Exception Handling
- Validation

---

## 📂 Project Structure

```
src
├── controller
│   ├── CustomerController
│   └── AccountController
├── dto
├── entity
│   ├── Customer
│   ├── DocumentType
│   └── Account
├── mapper
│   ├── CustomerMapper
│   └── AccountMapper
├── repository
│   ├── CustomerRepository
│   ├── DocumentTypeRepository
│   └── AccountRepository
├── service
│   ├── CustomerService
│   ├── AccountService
│   └── generator
│       ├── AccountNumberGenerator
│       └── IbanGenerator
├── validation
└── exception
```

---

## 🗄 Database

PostgreSQL

This project uses **Flyway** for database versioning and migration management.

Current migrations:

```text
V1__create_document_type_table.sql
V2__insert_document_types.sql
V3__create_customer_table.sql
V4__create_account_table.sql
```

Every time the application starts, Flyway automatically validates and applies pending database migrations.

---

## Tests

Current test coverage includes:

- Service Tests
- Controller Tests
- Integration Tests

Frameworks

- JUnit 5
- Mockito
- JaCoCo

---

## 🐳 Docker

The application can be started using Docker Compose.

```bash
docker compose up
```

---

### DevOps

- GitHub Actions
- Continuous Integration
- Automated Build
- Automated Tests

## 📖 API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## Roadmap

### Completed

- [x] Project setup
- [x] PostgreSQL
- [x] Docker Compose
- [x] Flyway
- [x] Document Types
- [x] Customer CRUD
- [x] Unit Tests
- [x] GitHub Actions
- [x] Swagger

### Planned

- [ ] Account Module
- [ ] Transaction Module
- [ ] Balance Management
- [ ] JWT Authentication
- [ ] Role-Based Authorization
- [ ] Integration Tests
- [ ] Testcontainers
- [ ] SonarCloud

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

## Project Progress

### v0.1.0
- Initial project setup
- Java 21
- PostgreSQL
- Docker Compose

### v0.2.0
- Flyway
- Database schema
- Document Types

### v0.3.0
- Customer CRUD
- Business validations
- Exception handling
- Unit tests

### v0.4.0
- GitHub Actions
- Continuous Integration
- JaCoCo
- Automated build
- Automated tests

### v0.5.0
- Account module implemented
- Account entity and database schema
- Checking and Savings account support
- Account business rules
- Account number generator
- Portuguese IBAN generator
- CRUD operations
- DTOs and MapStruct
- Unit tests
- Controller tests
- Repository integration tests
# BeachRent API

Final integrative project from the TodoCode Academy Testing & Software Quality course by Luisina de Paula.

**Academy Website:** https://todocodeacademy.com/

## What it does

REST API for beach equipment rental management with comprehensive unit and integration testing using JUnit 5 and Mockito.

## Database Schema

**Product:** id, name, category, stock, pricePerHour, active

**Rental:** id, productId, customerName, startTime, endTime, status

## Endpoints

### Products
- `POST /api/products` - Create product
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Rentals
- `POST /api/rentals` - Create rental
- `GET /api/rentals/{id}` - Get rental by ID
- `POST /api/rentals/{id}/return` - Return rental
- `POST /api/rentals/{id}/cancel` - Cancel rental

## Setup

### Local Development

1. Run the application:
```bash
mvn spring-boot:run
```

2. Access at: `http://localhost:8080`

3. H2 Console (optional): `http://localhost:8080/h2-console`
    - JDBC URL: `jdbc:h2:mem:beachrentdb`
    - Username: `your_username`
    - Password: `your_password`

### Run Tests
```bash
mvn test
```

## Tech Stack

Java 17, Spring Boot 3.5.8, Spring Data JPA, H2 Database, JUnit 5, Mockito, MockMVC, Bean Validation, Maven

## Features

- **Unit Testing:** 14 tests for Service layer with Mockito
- **Integration Testing:** 15 tests for Controller layer with MockMVC
- **Total Coverage:** 29 tests, 80%+ coverage
- **Input Validation:** Bean Validation with custom error messages
- **Given-When-Then:** BDD-style test structure
- **In-Memory Database:** H2 for fast testing and development
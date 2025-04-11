# Product Control API

This is a REST API project developed with the goal of consolidating my knowledge in Java, Spring Boot, and related
technologies. The application simulates product management and inventory control.

## Features

- **User Management**: Allows creation, update, and soft deletion of users. Each user has a
  role (`ADMIN`, `MANAGER`, `SALES`) and authentication is handled via JWT tokens.
- **Product Management**: Enables users to add, update, and delete products. Each product has attributes like name,
  price, and quantity.
- **Transaction Logging**: Tracks product stock movements (e.g., increasing or decreasing quantity) through
  transactions. Each transaction is associated with a user and a product.

## Technologies Used

- **Java 22**
- **Spring Boot**
- **Spring Security**
- **JPA/Hibernate**
- **PostgresSQL**
- **Flyway**
- **Maven**
- **Docker**
- **JUnit** and **Mockito**

## Prerequisites

- **Docker** installed.
- Environment variables set up.

## Environment Variables

The following environment variables need to be configured:

| Variable            | Description                      |
|---------------------|----------------------------------|
| `DATABASE_NAME`     | Name of the PostgresSQL database |
| `DATABASE_USER`     | Username for the database        |
| `DATABASE_PASSWORD` | Password for the database        |
| `JWT_SECRET`        | Secret key for JWT generation    |

## Setup and Run

1. Clone this repository:

2. Create a `.env` file in the root directory with the following content:
    ```env
    DATABASE_NAME=your_db
    DATABASE_USER=your_user
    DATABASE_PASSWORD=your_password
    JWT_SECRET=your_jwt_secret

3. Run the project using Docker Compose:
   ```bash
   docker compose up

## API Documentation

- **Swagger UI**: [here](http://localhost:64064/swagger-ui.html)

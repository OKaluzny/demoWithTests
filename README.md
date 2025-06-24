# Employee Management System

## Overview
This is a Spring Boot application that provides a comprehensive employee management system with both REST API and gRPC interfaces. The application allows you to create, read, update, and delete employee records, as well as manage related entities such as addresses and documents.

## Features
- RESTful API for employee management
- gRPC service for high-performance client-server communication
- PostgreSQL database for data persistence
- Flyway for database migrations
- Swagger UI for API documentation
- Spring Security for authentication and authorization
- Docker support for containerization
- JUnit and Mockito for testing

## Technologies
- Java 17
- Spring Boot 3.2.2
- Spring Data JPA
- Spring Security
- gRPC
- PostgreSQL
- H2 Database (for testing)
- Flyway
- Swagger/OpenAPI
- Docker
- Maven
- JUnit 5
- Mockito

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose (optional, for containerized deployment)

### Building the Application
```bash
mvn clean install
```

### Running the Application
#### Using Maven
```bash
mvn spring-boot:run
```

#### Using Docker
```bash
docker-compose up
```

### Accessing the Application
- REST API: http://localhost:8089
- Swagger UI: http://localhost:8089/swagger-ui.html
- gRPC: localhost:9090

## API Documentation

### REST API Endpoints

#### Employee Management
- `POST /api/users` - Create a new employee
- `GET /api/users` - Get all employees
- `GET /api/users/{id}` - Get an employee by ID
- `PUT /api/users/{id}` - Update an employee
- `DELETE /api/users/{id}` - Delete an employee
- `DELETE /api/users` - Delete all employees

#### Pagination and Filtering
- `GET /api/users/pages` - Get employees with pagination
- `GET /api/users/country` - Find employees by country with pagination and sorting
- `GET /api/users/names` - Find employees by name

#### Document Management
- `PUT /api/employees/{id}/documents` - Add a document to an employee
- `PATCH /api/employees/{id}/documents` - Remove a document from an employee

### gRPC Service
The application also provides a gRPC service for employee management with the following methods:
- `CreateEmployee` - Create a new employee
- `GetEmployee` - Get an employee by ID
- `UpdateEmployee` - Update an employee
- `DeleteEmployee` - Delete an employee

## Database Schema
The application uses the following main entities:
- `Employee` - Represents an employee with basic information
- `Address` - Represents an address associated with an employee
- `Document` - Represents a document associated with an employee

## Testing
The application includes unit tests and integration tests. You can run the tests using:
```bash
mvn test
```

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

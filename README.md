# Notes App – Fullstack Application

Fullstack notes management application.  
This repository contains the **backend REST API** built with Spring Boot.  
A **React frontend** will be integrated as the next step of the project.

The goal of this project is to demonstrate fullstack development skills, clean backend architecture, testing, and API documentation.


## Project Scope

- Backend: REST API for notes management
- Frontend (planned): React application consuming this API
- Architecture focused on maintainability and testability


## Backend Features

- Create, read, update and delete notes
- Archive and unarchive notes
- Tag management (add and remove tags)
- Filter notes by tag
- DTO-based API design
- Input validation with clear error messages
- Service layer with transactional logic
- OpenAPI / Swagger documentation
- Unit tests for service and controller layers


## Tech Stack

### Backend
- Java 11
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- Bean Validation
- Swagger / OpenAPI 3
- JUnit 5
- Mockito
- MockMvc

### Frontend (planned)
- React
- TypeScript
- REST API integration



### Layer Responsibilities

- Controller: HTTP endpoints and request handling
- Service: business logic and transactions
- Repository: persistence layer (Spring Data JPA)
- DTOs: API contracts
- Mappers: conversion between entities and DTOs


## API Documentation

The backend exposes OpenAPI documentation via Swagger.

After running the application, access:

http://localhost:8080/swagger-ui.html

The documentation includes:
- All available endpoints
- Request and response schemas
- Example payloads


## Main API Endpoints

### Notes
- POST /api/notes
- GET /api/notes
- GET /api/notes/{id}
- PUT /api/notes/{id}
- DELETE /api/notes/{id}

### Archive
- PUT /api/notes/{id}/archive
- PUT /api/notes/{id}/unarchive

### Tags
- PUT /api/notes/{noteId}/tags/{tagName}
- DELETE /api/notes/{noteId}/tags/{tagName}
- GET /api/notes/by-tag/{tagName}


## Testing

The backend includes unit tests covering:

- Service layer business logic
- Controller layer using MockMvc

Run tests with:

```bash
mvn test

### Running the Backend
mvn sprint-boot:run

The API will be available at:
http://localhost:8080

### Database
Uses H2 in-memory database
Automatically initialized on startup
No external configuration required

### Frontend Integration
The frontend will be developed as a separate module using React and will consume this API.

##Author

Florencia Echauri
Software Developer


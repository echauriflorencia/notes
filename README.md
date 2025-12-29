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


The backend is built with Spring Boot and exposes a REST API for managing notes and tags.

### Features implemented
- CRUD operations for notes
- Tag assignment to notes
- Global exception handling
- Validation with meaningful error responses
- Basic authentication using Spring Security
- Controller and integration tests with MockMvc

### Security
- HTTP Basic authentication
- Protected `/api/**` endpoints
- Public access to Swagger endpoints


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

mvn test

## Running the Backend
mvn sprint-boot:run

The API will be available at:
http://localhost:8080

## Database
Uses H2 in-memory database
Automatically initialized on startup
No external configuration required


## Frontend Integration
The frontend will be developed as a separate module using React and will consume this API.

## API Contract
This section describes the REST API contract exposed by the backend.
The contract is designed to be consumed by a frontend (React) with clear endpoints, predictable responses and consistent use of HTTP status codes.

### Base URL
/api/notes

### Data model
Note
```json
{ 
  "id": 1,
  "title": "Meeting notes",
  "content": "Discuss project milestones",
  "archived": false,
  "createdAt": "2025-12-29T15:30:00",
  "updatedAt": "2025-12-29T16:10:00"
}
```

| Field    | Type | Description 
| -------- | ------- | ------- |
| id  | Long    | Unique identifier of the note
| title | String     | Title of the note
| content    | String    | Content of the note
| archived    | Boolean    | Indicates if the note is archived
| createdAt | DateTime 	 | Date and time of creation
| updatedAt | DateTime 	 | Date and time of the last update

### Endpoints

#### Get all notes 
GET /api/notes
Successful response: Status 200 OK 
```json
{ 
  "id": 1,
   "title": "First note",
   "content": "This is my first note",
   "archived": false,
   "createdAt": "2025-12-29T15:30:00",
   "updatedAt": "2025-12-29T15:30:00"
}
```

#### Get note by id
GET /api/notes/{id}
Successful response: Status 200 OK
```json
{ 
   "id": 1,
   "title": "First note",
   "content": "This is my first note",
   "archived": false,
   "createdAt": "2025-12-29T15:30:00",
   "updatedAt": "2025-12-29T15:30:00"
}
```

Error: Status 404 Not Found
```json
{ 
   "message": "Note not found"
}
```

#### Create note
POST /api/notes
Request body
```json
{ 
  "title": "New note",
  "content": "This is a new note"
}
```

Successful response: Status 201 Created
```json
{ 
  "id": 2,
  "title": "New note",
  "content": "This is a new note",
  "createdAt": "2025-12-29T16:00:00",
  "updatedAt": "2025-12-29T16:00:00"
}
```

Validations: 
- title can't be empty
- content can't be empty

Error: Status 404 Bad Request
```json
{ 
  "message": "Validation error",
  "errors": {
    "title": "Title is required"
  }
}
```

#### Update an existing note
PUT /api/notes/{id}
Request body
```json
{ 
  "title": "Updated title",
  "content": "Updated content"
}
```

Successful response: Status 200 OK
```json
{ 
 "id": 1,
  "title": "Updated title",
  "content": "Updated content",
  "createdAt": "2025-12-29T15:30:00",
  "updatedAt": "2025-12-29T16:20:00"
}
```

Error: Status 404 Not Found
```json
{ 
   "message": "Note not found"
}
```

#### Delete note
DELETE /api/notes/{id}
Successful response: Status 204 No Content

Error: Status 404 Not Found
```json
{ 
   "message": "Note not found"
}
```

### General conventions
- All responses are delivered in JSON format.
- Standard HTTP status codes are used.
- Errors have a consistent structure.
- The backend does not handle session state (stateless).
- The contract is designed to be consumed by a SPA (React) frontend.

## Author

Florencia Echauri
Software Developer


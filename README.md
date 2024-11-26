# Library Management System
 
This is a Spring Boot-based application for managing an online library system. The project allows users to perform CRUD operations on books, authors, and publishers, and provides advanced search, sorting, and reporting features.
 
---
 
## Features
 
### 1. CRUD Operations
- **Books**: Add, update, delete, and retrieve books.
- **Authors**: Add, update, delete, and retrieve authors.
- **Publishers**: Add, update, delete, and retrieve publishers.
 
### 2. Advanced Features
- **Search Books by title**: Search books by title.
 
### 3. Exception Handling
Centralized exception handling ensures clear error messages:
- **ResourceNotFoundException**: Triggered when a resource (book, author, or publisher) is not found.
- **InvalidInputException**: Triggered when invalid input is provided.
- **General Exception**: Handles all other unexpected errors.
 
---
 
## Technologies Used
 
- **Java 17**: For Streams, Lambda expressions, and core programming.
- **Spring Boot**: For application setup and REST API creation.
- **H2 Database**: In-memory database for persistence.
- **Hibernate & JPA**: For ORM and entity management.
- **Git**: Version control for managing branches and commits.
 
---
 
## Setup Instructions
 
### 1. Clone the Repository
```bash
git clone [<repository-url>](https://github.com/trishna-123/Library-Management.git)
cd library-management

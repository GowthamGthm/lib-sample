
# Library Management System - Backend

This is the backend service for the Library Management System built with Java Spring Boot.

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

## Setup Instructions

1. **Database Setup**
   ```bash
   # Start PostgreSQL service
   sudo service postgresql start
   
   # Create database and tables
   sudo -u postgres psql -c "CREATE DATABASE librarydb;"
   sudo -u postgres psql -d librarydb -f ../db/setup.sql
   ```

2. **Application Configuration**
   - Database connection details are configured in `src/main/resources/application.properties`
   - Default database: `librarydb`
   - Default username: `postgres`
   - Default password: `postgres`

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   
   The application will start on `http://localhost:8080`

## Sample Login Credentials

- **Username:** admin
- **Password:** admin123

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/logout` - User logout
- `GET /api/auth/validate` - Validate session

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Books
- `GET /api/books` - Get all books
- `GET /api/books/{id}` - Get book by ID
- `GET /api/books/available` - Get available books
- `GET /api/books/search/title?title={title}` - Search books by title
- `GET /api/books/search/author?author={author}` - Search books by author
- `POST /api/books` - Create new book
- `PUT /api/books/{id}` - Update book
- `DELETE /api/books/{id}` - Delete book

### Borrowing
- `POST /api/borrowing/borrow` - Borrow a book
- `POST /api/borrowing/return` - Return a book
- `GET /api/borrowing/user/{userId}` - Get user's borrowed books
- `GET /api/borrowing/active` - Get all active borrowings
- `GET /api/borrowing/history` - Get borrowing history

## Features

- User authentication and session management
- User management (CRUD operations)
- Book management (CRUD operations)
- Book borrowing and returning system
- Search functionality for books
- CORS configuration for frontend integration

## Database Schema

The application uses PostgreSQL with the following main entities:
- **Users**: User account information
- **Books**: Book catalog with availability tracking
- **BorrowedBooks**: Borrowing transaction records

## Technology Stack

- Java 21
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- Maven

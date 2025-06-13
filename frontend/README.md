
# Library Management System - Frontend

This is the frontend application for the Library Management System built with Angular.

## Prerequisites

- Node.js 18 or higher
- npm 9 or higher
- Angular CLI 18 or higher

## Setup Instructions

1. **Install Dependencies**
   ```bash
   npm install
   ```

2. **Environment Configuration**
   - The API base URL is configured in `src/environments/environment.ts`
   - Default backend URL: `http://localhost:8080/api`

3. **Run the Application**
   ```bash
   ng serve
   ```
   
   The application will start on `http://localhost:4200`

## Sample Login Credentials

- **Username:** admin
- **Password:** admin123

## Features

### Authentication
- User login and registration
- Session management
- Route protection with guards
- HTTP interceptors for API authentication

### Dashboard
- Overview statistics (total books, users, borrowings)
- Recent activity displays
- Quick access to main features

### User Management
- View all users
- Add new users
- Delete users (except admin)
- User search and filtering

### Book Management
- View all books with search functionality
- Add new books
- Delete books
- Track available copies
- Search by title, author, or genre

### Borrowing System
- Borrow books for users
- Return borrowed books
- View active borrowings
- Available books summary

## Technology Stack

- Angular 18
- Bootstrap 5
- Font Awesome icons
- RxJS for reactive programming
- Angular Material (optional components)

## Project Structure

```
src/
├── app/
│   ├── components/          # Feature components
│   │   ├── login/
│   │   ├── register/
│   │   ├── dashboard/
│   │   ├── users/
│   │   ├── books/
│   │   └── borrowing/
│   ├── core/               # Core functionality
│   │   ├── guards/         # Route guards
│   │   ├── interceptors/   # HTTP interceptors
│   │   ├── models/         # Data models
│   │   └── services/       # API services
│   ├── shared/             # Shared components
│   │   └── navbar/
│   ├── app-routing.module.ts
│   ├── app.module.ts
│   └── app.component.ts
├── environments/           # Environment configurations
└── styles.scss            # Global styles
```

## API Integration

The frontend communicates with the Spring Boot backend through REST APIs:

- **Authentication:** `/api/auth/*`
- **Users:** `/api/users/*`
- **Books:** `/api/books/*`
- **Borrowing:** `/api/borrowing/*`

## Responsive Design

The application is fully responsive and works on:
- Desktop computers
- Tablets
- Mobile devices

## Development

### Code Scaffolding

Run `ng generate component component-name` to generate a new component.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

### Running Tests

Run `ng test` to execute the unit tests via Karma.

## Deployment

For production deployment:

1. Build the application:
   ```bash
   ng build --prod
   ```

2. Serve the `dist/` folder using a web server like Nginx or Apache.

3. Update the environment configuration to point to the production backend API.


# Task Manager API

A secure and scalable Spring Boot REST API for managing tasks with JWT authentication, role-based access control, and Swagger UI documentation. Designed using modern Java backend best practices.

---

## Key Features

- JWT-based Authentication
- Role-based Authorization (USER, ADMIN)
- CRUD Operations for Tasks
- Swagger UI for API Testing
- Layered Architecture (Controller → Service → Repository)
- MySQL Integration with Spring Data JPA
- Global Exception Handling

---

## Tech Stack

- Java 17, Spring Boot 3
- Spring Security, JWT
- MySQL, Spring Data JPA
- Swagger / OpenAPI 3
- Maven, Git, Eclipse or IntelliJ

---

## Project Structure

```
task-manager-api/
├── controller/
├── dto/
├── model/
├── repository/
├── security/
├── service/
├── config/
├── TaskManagerApplication.java
└── application.properties
```

---

## API Testing via Swagger

1. Start the application:

   ```bash
   mvn spring-boot:run
   ```

2. Open Swagger UI in browser:

   ```
   http://localhost:8080/swagger-ui/index.html
   ```

3. Steps to test:

   - Register a user via `POST /api/auth/register`
   - Log in via `POST /api/auth/login` and copy the token
   - Click "Authorize" in Swagger and paste `Bearer <your-token>`
   - Use task-related endpoints to perform CRUD operations

---

## Role-Based Access

| Role  | Permissions                       |
|-------|-----------------------------------|
| USER  | Can manage their own tasks        |
| ADMIN | Can access all tasks and users    |

Secure endpoints using:

```java
@PreAuthorize("hasRole('ADMIN')")
```

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL
- Git

### Setup Instructions

1. Clone the repository:

   ```bash
   git clone https://github.com/Sivaram-Kongari/task-manager-api.git
   cd task-manager-api
   ```

2. Configure database in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/task_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. Create the database in MySQL:

   ```sql
   CREATE DATABASE task_db;
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

---

## Optional: Create Admin User on Startup

Add this snippet in `CommandLineRunner` or similar:

```java
User admin = User.builder()
    .username("admin")
    .email("admin@task.com")
    .password(encoder.encode("admin123"))
    .role(Role.ADMIN)
    .build();
```

---

## Author

**Kongari Sivaram**  
GitHub: [github.com/Sivaram-Kongari](https://github.com/Sivaram-Kongari)

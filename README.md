# Arena Billiard

A full-stack billiard arena management application built with **Spring Boot 4.0**, **Java 21**, and **Vaadin 24**. Features a REST API for external clients and a Vaadin web UI for end users.

---

## Tech Stack

| Component   | Technology                  |
| ----------- | --------------------------- |
| Backend     | Spring Boot 4.0.6 / Java 21 |
| Frontend    | Vaadin 24.3.0               |
| Database    | H2 (Dev) / MySQL (Prod)     |
| ORM         | Spring Data JPA / Hibernate |
| Build Tool  | Maven                       |
| Utilities   | Lombok                      |

---

## Project Structure

```
src/main/java/com/bjorbun/billiard/
├── ArenaBilliardApplication.java
├── controller/       # REST API controllers
├── dto/              # Data Transfer Objects
├── model/            # JPA entities
├── repository/       # Spring Data JPA repos
├── service/          # Business logic
└── view/             # Vaadin views (pages)
```

---

## Quick Start (Local Dev)

### 1. Prerequisites

- Java 21+
- Maven 3.8+
- MySQL (for production environment)

### 2. Setup Environment

By default, the application will use the H2 in-memory database for development. If you want to use MySQL, make sure it is running and configured properly in your `application.properties`.

### 3. Run

**On Bash / Zsh:**

```bash
./mvnw spring-boot:run
```

**On Windows (PowerShell):**

```powershell
.\mvnw spring-boot:run
```

App will be available at **http://localhost:8080** (or whichever port is configured).

---

## Default Port

The application runs on **port 8080** by default. You can change this via `server.port` in `src/main/resources/application.properties`.

---

## Troubleshooting

| Issue                      | Solution                                                         |
| -------------------------- | ---------------------------------------------------------------- |
| `Port 8080 already in use` | Change the port in `application.properties` or kill the process using port 8080 |

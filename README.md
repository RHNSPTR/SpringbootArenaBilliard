# Car Rental System

A full-stack car rental management application built with **Spring Boot 4.0**, **Java 21**, and **Vaadin 25**. Features JWT-based REST API for external clients and a Vaadin web UI for end users.

---

## Tech Stack

| Component   | Technology                  |
| ----------- | --------------------------- |
| Backend     | Spring Boot 4.0.5 / Java 21 |
| Frontend    | Vaadin 25.1.7               |
| Database    | PostgreSQL (Neon / Docker)  |
| ORM         | Spring Data JPA / Hibernate |
| Auth (REST) | JWT (jjwt 0.11.5)           |
| Auth (UI)   | Spring Security + Sessions  |
| Build Tool  | Maven                       |

---

## Project Structure

```
src/main/java/com/example/rental_mobil/
├── RentalMobilApplication.java
├── AppConfig.java
├── config/           # Security, JWT filter, exception handler
├── controller/       # REST API controllers
├── model/            # JPA entities + enums
├── repository/       # Spring Data JPA repos
├── service/          # Business logic
├── ui/
│   ├── layout/       # MainLayout (app shell)
│   └── view/         # Vaadin views (pages)
│       └── admin/    # Admin-only views
```

---

## Quick Start (Local Dev)

### 1. Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL (or use the `docker-compose.local.yml`)

### 2. Setup Environment

```bash
cp .env.example .env
# Edit .env with your PostgreSQL credentials
```

### 3. Run

## Running Without Docker (Direct Maven)

**On Bash / Zsh:**

```bash
set -a && source .env && set +a && ./mvnw spring-boot:run
```

**On Windows (PowerShell):**

```powershell
Get-Content .env | ForEach-Object { if (\(_ -match '^([^=]+)=(.*)\)') { [System.Environment]::SetEnvironmentVariable(\(Matches[1],\)Matches[2], "Process") } }; .\mvnw spring-boot:run
```

App will be available at **http://localhost:9090**.

---

## Running With Docker

```bash
docker compose -f docker-compose.local.yml up -d --build
```

App will be available at **http://localhost:9090**.

---

## First Time Setup — Creating an Admin User

All new users register with `USER` role by default. To create an **ADMIN** user:

**Option A: Via SQL (Neon / any PostgreSQL client)**

```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'your-admin@email.com';
```

**Option B: Via Docker exec**

```bash
docker exec -it car-rental-db psql -U rental_user -d rental_db -c "UPDATE users SET role = 'ADMIN' WHERE email = 'your-admin@email.com';"
```

After this, log out and log back in — admin menu items will appear.

---

## Docker Deployment

### Production (Cloud DB — e.g. Neon)

```bash
# 1. Set .env with your Neon credentials
# 2. Build & run
docker compose --env-file .env up -d --build

# 3. App at http://localhost:9090
```

### Local Dev (with bundled PostgreSQL)

```bash
# 1. Set .env for local database:
#    SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/rental_db
#    SPRING_DATASOURCE_USERNAME=rental_user
#    SPRING_DATASOURCE_PASSWORD=rental_pass
#    DB_PORT=5433

# 2. Build & run
docker compose --env-file .env -f docker-compose.yml -f docker-compose.local.yml up -d --build

# 3. App at http://localhost:9090
```

### Stop

```bash
docker compose --env-file .env down
```

---

## Running Without Docker (Direct Maven)

```bash
# Load env vars and run
set -a && source .env && set +a && ./mvnw spring-boot:run

# Or build and run JAR
./mvnw clean package -DskipTests
java -jar target/rental-mobil-0.0.1-SNAPSHOT.jar
```

---

## Default Port

The application runs on **port 9090** by default. Change via `SERVER_PORT` in `.env`.

---

## Vaadin UI Pages

| Route             | Page         | Access        |
| ----------------- | ------------ | ------------- |
| `/sign-in`        | Login        | Public        |
| `/register`       | Register     | Public        |
| `/`               | Dashboard    | Auth required |
| `/cars`           | Car Catalog  | Auth required |
| `/book/new/{id}`  | New Booking  | Auth required |
| `/my-bookings`    | My Bookings  | Auth required |
| `/my-payments`    | My Payments  | Auth required |
| `/admin/bookings` | All Bookings | Admin only    |
| `/admin/payments` | Payment Mgmt | Admin only    |
| `/admin/users`    | User Mgmt    | Admin only    |

---

## REST API

Base URL: `http://localhost:9090/api/v1`

Full REST API documentation is available in [`BACKEND.md`](./docs/BACKEND.md) and includes the Postman collection (`postman.json`).

Key endpoints:

| Endpoint                | Method | Auth   |
| ----------------------- | ------ | ------ |
| `/auth/register`        | POST   | Public |
| `/auth/login`           | POST   | Public |
| `/auth/logout`          | POST   | Bearer |
| `/cars`                 | GET    | Bearer |
| `/cars`                 | POST   | Admin  |
| `/bookings`             | GET    | Admin  |
| `/bookings?userId={id}` | GET    | Bearer |
| `/bookings`             | POST   | Bearer |
| `/payments`             | GET    | Admin  |
| `/payments`             | POST   | Bearer |
| `/users/me`             | GET    | Bearer |
| `/users`                | GET    | Admin  |

---

## Environment Variables

| Variable                     | Description                     | Default      |
| ---------------------------- | ------------------------------- | ------------ |
| `SPRING_DATASOURCE_URL`      | PostgreSQL JDBC URL             | _(required)_ |
| `SPRING_DATASOURCE_USERNAME` | Database username               | _(required)_ |
| `SPRING_DATASOURCE_PASSWORD` | Database password               | _(required)_ |
| `SERVER_PORT`                | Application port                | `9090`       |
| `DB_PORT`                    | Local PostgreSQL port (compose) | `5432`       |

---

## Troubleshooting

| Issue                      | Solution                                                         |
| -------------------------- | ---------------------------------------------------------------- |
| `Port 9090 already in use` | `lsof -ti:9090 \| xargs kill -9`                                 |
| `Car not available`        | The car has conflicting active bookings — choose different dates |
| Can't see admin menu       | Your role is `USER`. Update to `ADMIN` via SQL (see above)       |
| Login doesn't persist      | Clear browser cookies for localhost:9090 and re-login            |
| Docker build is slow       | Only on first build — subsequent runs use cache                  |

## Frontend

For frontend documentation please refer to [FRONTEND.md](./docs/FRONTEND.md).

# FMHY-Clone

A curated links & resources directory — inspired by [FMHY](https://fmhy.pages.dev/) — built with **Spring Boot (Maven)**, **MySQL**, **Redis**, and **React**.

Content (categories, links, tags) is managed by a single admin through a protected admin panel. The public site is fully open, cached, and searchable.

> 🚧 **Status: Under active development.** Backend domain layer in progress; public API, admin API, security, caching, and the React frontend are not yet built.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 4.1 (Maven), Java 17 |
| Database | MySQL 8, schema managed via Flyway migrations |
| Caching | Redis |
| Auth | Spring Security + JWT (single admin role) |
| Frontend | React + Vite + React Router *(planned)* |
| Deployment | No Docker — runs directly via Maven Wrapper |

---

## Data Model

```
Category ──1-to-many──▶ Link ◀──many-to-many──▶ Tag
                          │
                    status: WORKING / BROKEN / UNVERIFIED

AdminUser (standalone, used for admin login)
```

- **Category** — e.g. "Streaming", "Gaming". Has a unique `slug` for clean URLs.
- **Link** — belongs to one Category, can have multiple Tags, tracks a `status`.
- **Tag** — e.g. "Free", "No ads", shared across many links.
- **AdminUser** — the single account used to manage content.

Schema is defined in [`src/main/resources/db/migration/V1__init_schema.sql`](backend/src/main/resources/db/migration/V1__init_schema.sql) and applied automatically by Flyway on startup.

---

## Project Structure

```
fmhy-clone/
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/fmhyclone/
│       │   │   ├── FmhyCloneApplication.java
│       │   │   ├── entity/
│       │   │   ├── repository/
│       │   │   ├── service/          (planned)
│       │   │   ├── controller/       (planned)
│       │   │   ├── security/         (planned)
│       │   │   └── exception/        (planned)
│       │   └── resources/
│       │       ├── application.yml
│       │       └── db/migration/
│       │           └── V1__init_schema.sql
│       └── test/
└── frontend/          (planned)
```

---

## Prerequisites

- **JDK 17**
- **MySQL 8.x**, running locally
- **Redis**, running locally (via WSL on Windows, or natively on macOS/Linux)
- No Docker required — everything runs directly on the host machine

---

## Setup

### 1. Database

Create the database and a dedicated app user (don't use `root` for the application):

```sql
CREATE DATABASE fmhy_clone CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'fmhy_app'@'localhost' IDENTIFIED BY 'your_strong_password_here';
GRANT ALL PRIVILEGES ON fmhy_clone.* TO 'fmhy_app'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Redis

Start Redis and confirm it's responding:

```bash
redis-cli ping
# should return PONG
```

### 3. Configure the app

Edit `backend/src/main/resources/application.yml` (or set environment variables) with your MySQL credentials and a JWT secret:

```yaml
spring:
  datasource:
    username: fmhy_app
    password: your_strong_password_here
app:
  jwt:
    secret: a-long-random-secret-at-least-256-bits
```

### 4. Run

From the `backend/` directory:

```bash
./mvnw spring-boot:run       # macOS/Linux
.\mvnw.cmd spring-boot:run   # Windows
```

On first run, Flyway automatically creates all tables in `fmhy_clone`. You should see:

```
Started FmhyCloneApplication in X.XXX seconds
```

The API will be available at `http://localhost:8080`.

---

## Roadmap

- [x] Architecture & tech decisions
- [x] Database schema design
- [x] Spring Boot project setup (Maven, Flyway, MySQL, Redis, Security deps)
- [ ] Core domain layer (entities, repositories)
- [ ] Public REST API (browse categories, list/search links)
- [ ] Admin REST API (secured CRUD)
- [ ] Security (Spring Security + JWT)
- [ ] Redis caching wired into services
- [ ] Logging & global exception handling
- [ ] React frontend (public pages)
- [ ] React frontend (admin panel)
- [ ] Polish & deployment

---

## License

IDK

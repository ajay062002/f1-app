#  F1 Live – Spring Boot F1 Portal

A full-stack F1 fan portal with live driver and team data, standings, a circuit lap simulator, JWT authentication, weekly race digest emails, and an AI chatbot.

---

## What it does

- **Drivers & Teams** — grid of all 2025 drivers with stats modals; team cards with roster, year-by-year stats, driver history tabs
- **Standings** — driver and constructor standings with search and team filter
- **Circuits** — animated circuit simulator with lap timer and history
- **Auth** — register, login, JWT session (BCrypt password hashing)
- **Email** — welcome email on registration; automated weekly race digest every Friday at 6 PM via `@Scheduled`
- **AI Chatbot** — floating assistant answering F1 questions (drivers, stats, rules, teammates)
- **Light/Dark mode** — theme toggle persisted in localStorage

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot (Web, JPA, Mail, Security, Scheduler) |
| Database | MySQL 8 |
| Frontend | HTML / CSS / Vanilla JS |
| Auth | Spring Security + JWT (BCrypt) |
| Email | JavaMailSender (Gmail SMTP) |

---

## Project Structure

```
src/main/java/com/example/f1app/
├── config/
│   ├── SecurityConfig.java       # JWT filter chain, CORS, password encoder
│   ├── WebConfig.java            # MVC config
│   └── GlobalExceptionHandler.java
│
├── controller/
│   ├── AuthController.java       # /api/auth/register, /login, /me
│   ├── DriverController.java     # /api/drivers
│   ├── TeamController.java       # /api/teams
│   ├── StandingsController.java  # /api/standings
│   ├── CircuitController.java    # /api/circuits
│   ├── ChatController.java       # /api/chat/ask
│   ├── NewsletterController.java # /api/newsletter/send-weekly
│   ├── LiveController.java       # /api/live
│   └── dto/                      # Request/response DTOs
│
├── service/
│   ├── ChatService.java          # F1 question routing logic
│   ├── ChatData.java             # Static F1 data (drivers, teams, stats)
│   ├── EmailService.java         # Welcome + custom emails via JavaMailSender
│   ├── NewsletterService.java    # Bulk send logic, user filtering, debug stats
│   ├── WeeklyDigestJob.java      # @Scheduled Friday 6PM digest
│   └── UserService.java
│
├── model/                        # JPA entities (User, Driver, Team, Circuit...)
├── repository/                   # Spring Data JPA repositories
└── F1AppApplication.java
```

---

## Running Locally

### Prerequisites
- Java 17+, Maven, MySQL 8

### 1. Create the database
```sql
CREATE DATABASE f1_portal;
```

### 2. Set environment variables

**Windows PowerShell:**
```powershell
$env:DB_USER="root"
$env:DB_PASS="your_db_password"
$env:MAIL_USER="your_gmail@gmail.com"
$env:MAIL_PASS="your_16_char_app_password"
```

**Linux/macOS:**
```bash
export DB_USER=root
export DB_PASS=your_db_password
export MAIL_USER=your_gmail@gmail.com
export MAIL_PASS=your_16_char_app_password
```

### 3. Run
```bash
mvn spring-boot:run
```

Open [http://localhost:1947/index.html](http://localhost:1947/index.html)

> Gmail requires a 16-character **App Password** (not your account password). Enable 2-Step Verification first, then generate one at myaccount.google.com → Security → App passwords.

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register + sends welcome email |
| POST | `/api/auth/login` | Login, returns session |
| GET | `/api/auth/me` | Current session info |
| GET | `/api/drivers` | All drivers |
| GET | `/api/teams` | All teams |
| GET | `/api/standings` | Driver + constructor standings |
| GET | `/api/circuits` | Circuit list |
| POST | `/api/chat/ask` | AI chatbot query |
| POST | `/api/newsletter/send-weekly` | Trigger digest email to all users |
| GET | `/api/newsletter/all-users` | User list (admin) |
| GET | `/api/test/digest` | Manually trigger weekly digest |

---

## Source

[github.com/ajay062002/f1-app](https://github.com/ajay062002/f1-app)

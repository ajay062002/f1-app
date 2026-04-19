#  F1 Live – Spring Boot F1 Portal

A full-stack F1 web application built with Java Spring Boot and vanilla JS. Includes live driver/team/standings pages, a circuit simulator, user authentication, and automated email notifications.

---

## Features

- **Drivers** — grid of all 2025/26 drivers with stats modal (team, number, nationality)
- **Teams** — team cards with roster, year-by-year stats, and driver history tabs
- **Standings** — driver and constructor standings with search and team filter
- **Circuits** — animated F1 circuit simulator with lap timer and lap history
- **Authentication** — user registration, login/logout, JWT session
- **Email Notifications** — welcome email on registration + weekly race digest (Friday 6 PM)
- **AI Chatbot** — floating assistant powered by `/api/chat/ask` endpoint
- **Light/Dark Mode** — theme toggle persisted in localStorage across all pages
- **Countdown** — live countdown to next race loaded from `f1-data.json`

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot (Web, JPA, Mail, Scheduler) |
| Database | MySQL 8 |
| Frontend | HTML / CSS / Vanilla JS |
| Auth | Spring Security + JWT |
| Email | JavaMailSender (Gmail SMTP) |

---

## Setup

### Prerequisites
- Java 17+
- Maven
- MySQL 8+

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
$env:MAIL_PASS="your_16_char_gmail_app_pass"
```

**Linux/macOS:**
```bash
export DB_USER=root
export DB_PASS=your_db_password
export MAIL_USER=your_gmail@gmail.com
export MAIL_PASS=your_16_char_gmail_app_pass
```

### 3. Run
```bash
mvn spring-boot:run
```

Visit: [http://localhost:1947/index.html](http://localhost:1947/index.html)

---

## Gmail App Password

1. Enable **2-Step Verification** on your Gmail account
2. Generate a **16-character App Password**
3. Use that as `MAIL_PASS`

---

## Email Endpoints

| Endpoint | Description |
|----------|-------------|
| Auto on register | Welcome email |
| Every Friday 6 PM | Weekly race digest |
| `GET /api/test/digest` | Trigger digest manually |
| `GET /api/test/send?to=email@example.com` | Send test email |

---

## Share with ngrok
```bash
ngrok http 1947
```

---

## Common Issues

- **DB connection fails** → Check `DB_USER`/`DB_PASS`, ensure `f1_portal` database exists
- **Email not sending** → Use a Gmail App Password (not your account password)
- **Port in use** → Change `server.port` in `application.properties`
- **CORS issues** → Use the ngrok HTTPS URL

---

## Author

[Ajay Thota](https://github.com/ajay062002)

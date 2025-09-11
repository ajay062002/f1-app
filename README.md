# Aj F1 Live – Spring Boot F1 Portal

A Spring Boot web app with:
- Live F1 pages (drivers, teams, standings, circuits)
- User registration & authentication
- Email notifications (welcome + weekly digest before races)
- Built with **Java 17 + Spring Boot + MySQL**

---

## 🚀 Setup Instructions

### 1. Prerequisites
- Java 17 (or compatible JDK)
- Maven (`mvn -v`)
- MySQL 8+
- (Optional) ngrok – for sharing the app online

---

### 2. Database
Create the database in MySQL:
```sql
CREATE DATABASE f1_portal;
```

---

### 3. Environment Variables
Set these before running the app.

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

✅ These values are used automatically in `application.properties`.

---

### 4. Gmail App Password
- Enable **2-Step Verification** on your Gmail account  
- Generate a **16-character App Password**  
- Use that as `MAIL_PASS`

---

### 5. Run the App
From the project root (`pom.xml` is here):
```bash
mvn spring-boot:run
```
Visit: [http://localhost:1947/index.html](http://localhost:1947/index.html)

---

## 📩 Email Features
- **Welcome Email** → Sent automatically when a new user registers  
- **Weekly Digest** → Scheduled every Friday at 6 PM  
- **Manual Test** → `GET http://localhost:1947/api/test/digest`  
- **Custom Test Email** → `GET http://localhost:1947/api/test/send?to=someone@example.com`  

---

## 🌍 Share Your App (with ngrok)
Start ngrok:
```bash
ngrok http 1947
```
Use the HTTPS URL shown by ngrok to share your app publicly.

---

## ⚡ Common Issues
- **DB connection fails** → Check `DB_USER/DB_PASS`, ensure DB exists  
- **Email not sending** → Use Gmail App Password, check spam  
- **Port in use** → Change `server.port` in `application.properties`  
- **CORS issues** → Use ngrok HTTPS link  

---

## 🛠 Tech Stack
- Java 17  
- Spring Boot (Web, JPA, Mail, Scheduler)  
- MySQL  
- HTML/CSS/JS  

---

## 👤 Author
Ajay — AI-assisted (GPT) development to speed up integration and fixes.

# 🏨 Ocean View Resort Reservation Management System

> A layered, test-driven Java web application for managing hotel reservations, billing, reporting, authentication, and revenue tracking.

![Java](https://img.shields.io/badge/Java-17-blue)
![Build](https://img.shields.io/badge/Build-Maven-blue)
![Testing](https://img.shields.io/badge/Tests-JUnit%205-green)
![CI](https://img.shields.io/badge/CI-GitHub%20Actions-success)
![License](https://img.shields.io/badge/License-Academic-lightgrey)

---

## 📑 Table of Contents

- [Overview](#overview)
- [System Features](#system-features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Installation & Build](#installation--build)
- [Database Table Structures](#database-table-structures)
- [How to Run This Project](#how-to-run-this-project)
- [Automated Testing](#automated-testing)
- [Code Coverage (JaCoCo)](#code-coverage-jacoco)
- [Continuous Integration](#continuous-integration)
- [Project Structure](#project-structure)
- [Security & Validation](#security--validation)
- [Academic Objectives Achieved](#academic-objectives-achieved)
- [License](#license)

---

<a id="overview"></a>
## 📖 Overview

<p align="center">
  <img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEj3T5-inXSDbzYwopqMkJEkYBbhU1zpkIuxwY1fBZMSDsmr8Si3SaFnv776a_hrYoeuV7Z14aMArwUL1S0NhNYyMc22j1pTU7slS9dO-mP4sVADnLBl1vfj-bdl4VXGaYbncpMQnnkikJySYsU4OBnWE1U4I9a8o2WDJivXtZetWbEip_NmVXXv0Pn0_6E/s1600/Screenshot%202026-03-03%20140707.png" alt="Ocean View Resort ERD" width="600"/>
</p>


The **Ocean View Resort Reservation Management System** is a multi-layered Java web application developed using modern software engineering principles.

It provides a complete backend system for:

- Reservation management  
- Room administration  
- Secure role-based authentication  
- Guest billing and checkout  
- Revenue tracking (Daily & Monthly)  
- Dashboard analytics & visualization  

The project emphasizes:

- Clean layered architecture  
- Test-Driven Development (TDD)  
- Automated unit testing  
- Continuous Integration (CI/CD)  
- Professional Git version control  

---

<a id="system-features"></a>
## 🚀 System Features

| Module | Features Implemented | Status |
|--------|----------------------|--------|
| 🔐 Authentication | Admin & Staff login, role-based access control | ✅ Complete |
| 📝 Reservation Management | Booking system, search by reservation number, delete option | ✅ Complete |
| 📧 Notifications | Automated email confirmation on booking | ✅ Complete |
| 🏨 Room Management | Room data maintenance, availability tracking | ✅ Complete |
| 📊 Availability Control | Automatic & manual room availability updates | ✅ Complete |
| 💳 Billing System | Guest checkout, bill generation, manual checkout option | ✅ Complete |
| 🖨 Reporting | Daily & monthly revenue reporting | ✅ Complete |
| 📈 Dashboard Analytics | Occupancy tracking, revenue calculation (LKR), 7-day trend analysis | ✅ Complete |
| 🎨 UI/UX | Modernized dashboard, donut chart, animated counters | ✅ Complete |
| 🧪 Automated Testing | 26 structured JUnit test cases | ✅ Complete |
| 🔁 CI/CD | GitHub Actions automated build & test pipeline | ✅ Complete |

---

<a id="technology-stack"></a>
## 🛠 Technology Stack

### 🔹 Backend
- Java 17
- Jakarta Servlet API
- JDBC
- MySQL

### 🔹 Frontend
- HTML5
- CSS3
- JavaScript

### 🔹 Build Tool
- Maven

### 🔹 Database
- MySQL

### 🔹 Testing & Quality
- JUnit 5
- JaCoCo (Code Coverage)

### 🔹 DevOps
- Git
- GitHub
- GitHub Actions

### 🔹 Architecture Pattern
- Layered Architecture (MVC + Service + DAO)

---

<a id="architecture"></a>
## 🏗 Architecture

The system follows a **Layered Architecture** design:

```
Client (Browser)
        ↓
Controller Layer (Servlets)
        ↓
Service Layer (Business Logic & Validation)
        ↓
DAO Layer (Database Access)
        ↓
MySQL Database
```

### Layer Breakdown

**Presentation Layer**
- Handles HTTP requests and responses
- JSP-based UI rendering

**Service Layer**
- Business logic
- Reservation validation
- Revenue calculations
- Exception handling

**DAO Layer**
- SQL operations
- Data persistence
- Database transaction handling

**Utility Layer**
- DBConnection
- Password hashing utilities
- Email service

### Architectural Benefits

- Clear separation of concerns  
- High maintainability  
- Scalability-ready structure  
- Fully testable business logic  
- Clean dependency flow  

---

<a id="installation--build"></a>
## ⚙️ Installation & Build

### 1️⃣ Clone Repository

```bash
git clone https://github.com/SACHX3/OceanViewResort.git
cd OceanViewResort
```

### 2️⃣ Build Project

```bash
mvn clean install
```

Generated artifact:

```
target/*.war
```

Deploy the WAR file to a servlet container such as **Apache Tomcat**.

---

<a id="how-to-run-this-project"></a>
## ▶️ How to Run This Project

<a id="database-table-structures"></a>
### 🗄 Database Table

Below are the main tables:

```
users
room_types
rooms
reservations
billing
```
Relationships:

```
room_types → rooms → reservations → billing  
users → reservations  
```
All tables use PK & FK constraints for referential integrity.

### 1️⃣ Database Setup

Create MySQL database:

```
ocean_view_reservation
```

Using MySQL CLI:

```bash
mysql -u root -p
```

```sql
CREATE DATABASE ocean_view_reservation;
EXIT;
```

Import SQL file:

```bash
mysql -u root -p ocean_view_reservation < ocean_view_reservation.sql
```

Ensure your `DBConnection.java` uses:

```
DB_HOST=localhost
DB_PORT=3306
DB_NAME=ocean_view_reservation
DB_USER=root
DB_PASS=
```

Time zone configured as:

```
serverTimezone=Asia/Colombo
```

---

### 2️⃣ Apache Tomcat Setup

Download **Apache Tomcat 9+**:

https://tomcat.apache.org/download-90.cgi

Build project:

```bash
mvn clean install
```

WAR file generated:

```
target/*.war
```

Copy WAR file into:

```
apache-tomcat/webapps/
```

Start Tomcat:

Windows:
```
startup.bat
```

Linux/macOS:
```
./startup.sh
```

Access application:

```
http://localhost:8080/OceanViewResort/
```

---

<a id="automated-testing"></a>
## 🧪 Automated Testing

This project follows **Test-Driven Development (TDD)**:

- 🔴 Red – Write failing test  
- 🟢 Green – Implement minimal code  
- 🔵 Refactor – Improve code quality  

### Run Tests

```bash
mvn clean test
```

Build will fail automatically if any test fails.

---

<a id="code-coverage-jacoco"></a>
## 📊 Code Coverage (JaCoCo)

Generate coverage report:

```bash
mvn clean test
```

Open:

```
target/site/jacoco/index.html
```

---

<a id="continuous-integration"></a>
## 🔁 Continuous Integration

The project integrates **GitHub Actions** for automated CI validation.

Workflow file:

```
.github/workflows/ci.yml
```

---

<a id="project-structure"></a>
## 🗂 Project Structure

```
OceanViewReservationSystem
│
├── src
│   ├── main/java/com/oceanview
│   │   ├── controller
│   │   ├── service
│   │   ├── dao
│   │   ├── model
│   │   └── util
│   │
│   └── test/java/com/oceanview
│       ├── controller
│       ├── service
│       └── dao
│
├── .github/workflows/ci.yml
├── pom.xml
└── README.md
```

---

<a id="security--validation"></a>
## 🔐 Security & Validation

- Role-based authentication  
- Input validation  
- Reservation date validation  
- Duplicate revenue prevention  
- Structured exception handling  

All financial calculations are unit-tested to prevent inconsistencies.

---

<a id="academic-objectives-achieved"></a>
## 🎯 Academic Objectives Achieved

- ✔ Test-Driven Development (TDD)  
- ✔ JUnit 5 automated testing  
- ✔ CI/CD automation with GitHub Actions  
- ✔ Professional Git workflow  
- ✔ Layered enterprise architecture  
- ✔ Validation & exception handling  
- ✔ Revenue accuracy assurance  
- ✔ Requirement-to-test traceability  

---

## 👩‍💻 Author

**Sameera Chathuranga**  
BSc (Hons) Software Engineering  
ICBT Campus

---

<a id="license"></a>
## 📄 License

Academic project — intended for educational and evaluation purposes.


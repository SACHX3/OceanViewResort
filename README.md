# 🏨 Ocean View Resort Reservation Management System

> A layered, test-driven Java web application for managing hotel reservations, billing, reporting, authentication, and revenue tracking.

![Java](https://img.shields.io/badge/Java-17-blue)
![Build](https://img.shields.io/badge/Build-Maven-blue)
![Testing](https://img.shields.io/badge/Tests-JUnit%205-green)
![CI](https://img.shields.io/badge/CI-GitHub%20Actions-success)
![License](https://img.shields.io/badge/License-Academic-lightgrey)

---

## 📑 Table of Contents

- [Overview](#-overview)
- [System Features](#-system-features)
- [Technology Stack](#-technology-stack)
- [Architecture](#-architecture)
- [Installation & Build](#-installation--build)
- [Automated Testing](#-automated-testing)
- [Code Coverage (JaCoCo)](#-code-coverage-jacoco)
- [Continuous Integration](#-continuous-integration)
- [Project Structure](#-project-structure)
- [Release History](#-release-history)
- [Security & Validation](#-security--validation)
- [Academic Objectives Achieved](#-academic-objectives-achieved)
- [License](#-license)

---

## 📖 Overview

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

## 🛠 Technology Stack

### 🔹 Backend
- Java 17
- Jakarta Servlet API
- JSP
- JDBC

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

## ⚙️ Installation & Build

### 1️⃣ Clone Repository

```bash
git clone https://github.com/SACHX3/OceanViewReservationSystem.git
cd OceanViewReservationSystem
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

## 🧪 Automated Testing

This project follows **Test-Driven Development (TDD)**:

- 🔴 Red – Write failing test  
- 🟢 Green – Implement minimal code  
- 🔵 Refactor – Improve code quality  

### Testing Coverage

- 26 structured unit tests  
- Service layer validation tests  
- DAO interaction tests  
- Revenue calculation verification  
- Reservation logic validation  

### Run Tests

```bash
mvn clean test
```

Build will fail automatically if any test fails.

---

## 📊 Code Coverage (JaCoCo)

Generate coverage report:

```bash
mvn clean test
```

Open:

```
target/site/jacoco/index.html
```

Coverage includes:

- Business logic branches  
- Validation paths  
- DAO operations  

---

## 🔁 Continuous Integration

The project integrates **GitHub Actions** for automated CI validation.

Workflow file:

```
.github/workflows/ci.yml
```

### Pipeline Actions

- Checkout repository  
- Setup Java 17  
- Run `mvn clean install`  
- Execute all unit tests  
- Fail build on test failure  
- Generate build artifact  

### CI Guarantees

- Every push is validated  
- No unstable code enters main branch  
- Continuous quality assurance  

---

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

## 📦 Release History

### 🚀 v1.0 — Core Reservation System
- Reservation booking functionality
- Email notifications
- Admin & staff authentication
- Dashboard creation

### 🔍 v1.1 — Reservation Management Enhancements
- Reservation list view
- Delete reservation functionality
- Search by reservation number

### 🛠 v1.2 — Role Management Fix
- Corrected role update logic
- Ensured database persistence
- Improved validation checks

### 🏨 v1.3 — Room Availability Management
- Check available rooms feature
- Automatic & manual availability updates

### 🏢 v1.4 — Room Management Module
- Room data maintenance
- Admin dashboard room control

### 💳 v1.5 — Billing & Reporting
- Guest checkout system
- Bill search & print
- Manual checkout option
- Reservation detail table view

### 📊 v1.6 — Dashboard & Revenue Tracking
- Daily reservation count
- Check-in/check-out monitoring
- Occupied room tracking
- Daily revenue calculation (LKR)
- Monthly revenue calculation (LKR)

### 🎨 v1.7 — UI Redesign & Visualization
- Modern interface theme
- Donut chart for occupancy
- 7-day revenue trend analysis
- Animated statistic counters

### 🧪 v1.8 — Full Test Automation & CI/CD
- 26 automated test cases
- PASS/FAIL validation demonstration
- CI pipeline integration
- Automated build & test execution

### 📘 v1.9 — Coverage Reporting & Documentation Enhancement

- Integrated JaCoCo test coverage reporting
- Configured Maven coverage report generation
- Added structured and comprehensive README
- Improved formatting, setup instructions, and project structure documentation

---

## 🔐 Security & Validation

The system enforces:

- Role-based authentication  
- Input validation on all endpoints  
- Reservation date validation  
- Duplicate revenue prevention  
- Structured exception handling  

All financial calculations are unit-tested to prevent inconsistencies.

---

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

## 📄 License

Academic project — intended for educational and evaluation purposes.
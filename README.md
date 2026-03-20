# Newtome 🛒

[Live App](https://dxsg03couz5uo.cloudfront.net)

Newtome is a full-stack web application designed to support user accounts, listings, and catalog management with secure authentication and scalable backend architecture.

It highlights advanced backend development concepts using Spring Boot, including JWT-based security, database migration management, and production-ready deployment practices.

---

## 🔧 Tech Stack

* **Frontend:** Angular (Standalone Components, Routing, Services)
* **Backend:** Java 21, Spring Boot (REST API)
* **Security:** JWT Authentication (custom filter + token service)
* **Database:** MySQL (Dockerized)
* **Database Migrations:** Flyway
* **Cloud & Deployment:**

  * AWS S3 (image storage)
  * AWS CloudFront (CDN)
  * Docker (containerized backend + DB)
* **Tools:** Postman, Git, IntelliJ, VS Code

---

## 🧱 Architecture Overview

The backend follows a **layered architecture** to ensure maintainability and scalability:

* **Controller Layer** → Handles HTTP requests and responses
* **Service Layer** → Contains business logic
* **Repository Layer** → Manages database interactions
* **DTOs & Mappers** → Separates internal models from API contracts

### 📂 Core Modules

* **Users** → Authentication and account management
* **Catalog** → Listing structure and organization
* **Offers** → Buyer/seller offer workflow
* **Uploads** → File handling via AWS S3
* **Security** → JWT authentication and request filtering

---

## 🔐 Security Implementation

* Implemented **JWT-based authentication** using:

  * Custom `JwtAuthFilter`
  * Token generation and validation service
* Secured API endpoints so only authenticated users can access protected resources
* Configured CORS for safe frontend-backend communication

---

## 🗄️ Database & Migrations (Flyway)

The application uses **Flyway** to manage database schema changes in a safe and version-controlled way.

* Existing production schema was **baselined** into Flyway
* All schema changes are tracked via versioned migration files:

  * `V1__initial_schema.sql`
  * `V2__add_last_login_to_users.sql`
* Migrations run automatically on application startup

### ✅ Benefits

* Prevents accidental schema drift
* Ensures consistent environments (dev, staging, production)
* Enables safe, incremental database evolution

---

## 📦 Features

* User registration and authentication
* Secure login with token-based authorization
* RESTful APIs for listings and catalog management
* File upload functionality using AWS S3
* CDN-backed image delivery via CloudFront
* Modular backend structure for scalability

---

## 💰 Offer System

A key feature of Newtome is its **offer-based marketplace workflow**:

* Users can submit offers on listings
* Sellers can review and **accept or reject offers**
* Backend handles **offer state transitions**
* Implements real-world buyer/seller interaction logic beyond CRUD

---

## ☁️ Deployment

The application is deployed using a **containerized architecture**:

* Backend runs in a Docker container
* MySQL runs in a separate Docker container
* Frontend is hosted via AWS and served through CloudFront

### Key Highlights

* Environment-based configuration using Spring profiles
* Secure handling of secrets via environment variables
* Scalable infrastructure using AWS services

---

## 🚀 Key Learning Outcomes

* Designed and implemented secure REST APIs using Spring Boot
* Applied **JWT authentication and request filtering**
* Introduced **Flyway for database version control**
* Built scalable backend systems using **layered architecture**
* Integrated Angular frontend with RESTful backend
* Deployed a full-stack application using **Docker and AWS**

---

## 🔮 Future Enhancements

* Role-based authorization (Admin/User roles)
* Pagination and filtering for listings
* CI/CD pipeline (GitHub Actions or AWS)
* MongoDB integration for flexible data models
* Improved frontend state management (NgRx or Signals)

---

## 📮 Contact

**Ricky Diaz**
📧 [tampacustoms@yahoo.com](mailto:tampacustoms@yahoo.com)
📱 813-352-4525

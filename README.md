Full Stack Authentication App - Spring Boot
----------------------------------------------
A complete authentication system built using Spring Boot on the backend.
Supports JWT-based authentication with username/password login

BackEnd
--------
. Spring Boot 3.x

. Spring Security 6.x

. Spring Data JPA (MySQL)

. OAuth2 Client (Google, GitHub)

. JWT Authentication

. Lombok + HikariCP

Project Structure
------------------
auth-app-springboot
├── backend/     # Spring Boot Backend

├── src/

├── pom.xml
 
└── application.yml

└── README.md

⚙️ Backend Setup (Spring Boot)
-------------------------------

🧩 Prerequisites

. Java 17+

. Maven 3.9+

. MySQL (or compatible database)

. Git

🧰 Steps to Run Backend
---------------------------
. Navigate to the backend folder:

cd backend

. Create a new database:

CREATE DATABASE auth_app;

. Configure application.yml:

 server:
  port: 8081

 spring:
  application:
    name: auth-backend
  datasource:
    url: jdbc:mysql://localhost:3306/auth_app
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect

security:
  jwt:
    secret: ${JWT_SECRET}
    issuer: auth-backend
    access-ttl-seconds: 900
    refresh-ttl-seconds: 1209600
    refresh-cookie-name: refresh_token
    cookie-secure: false
    cookie-same-site: Lax

. Set environment variables:

export JWT_SECRET="your-random-long-secret"
export GOOGLE_CLIENT_ID="your-google-client-id"
export GOOGLE_CLIENT_SECRET="your-google-client-secret"
export GITHUB_CLIENT_ID="your-github-client-id"
export GITHUB_CLIENT_SECRET="your-github-client-secret"

. Run the Spring Boot app:

mvn spring-boot:run

📍 Backend runs on http://localhost:8080
--------------------------------------------






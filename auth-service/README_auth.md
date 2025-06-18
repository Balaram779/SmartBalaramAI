# 🛡️ Auth-Service (SmartBalaramAI)

This is the authentication and authorization microservice of the **SmartBalaramAI** project. It handles user registration, login, and JWT-based role validation (USER/ADMIN).

---

## 🔧 Tech Stack

- Java 17  
- Spring Boot  
- Spring Security + JWT  
- Maven  
- JPA + H2 (or any DB)  
- Swagger/OpenAPI  
- JUnit, Mockito

---

## 📦 Key Features

- ✅ User Registration (email-based)  
- ✅ Secure Login with JWT token generation  
- ✅ Role-based access control (`USER`, `ADMIN`)  
- ✅ JWT parsing and validation filter  
- ✅ Token expiration + refresh support (optional)  
- ✅ Unit + Integration tests

---

## 🚀 Running the Service

```bash
# Clone the project
git clone https://github.com/smartbalaram/auth-service.git
cd auth-service

# Build and run
mvn clean install
mvn spring-boot:run
```

Swagger UI:  
👉 http://localhost:8080/swagger-ui/index.html

---

## 📁 Endpoints

| Method | Endpoint               | Description          | Access  |
|--------|------------------------|----------------------|---------|
| POST   | `/api/v1/auth/register` | Register new user    | Public  |
| POST   | `/api/v1/auth/login`    | Login & get token    | Public  |
| GET    | `/api/v1/demo/user`     | USER endpoint        | USER    |
| GET    | `/api/v1/demo/admin`    | ADMIN endpoint       | ADMIN   |

---

## 🔐 JWT Flow (Simplified)

1. `POST /auth/login`  
   → Returns JWT Token

2. Frontend sends JWT in `Authorization: Bearer <token>`

3. Filter (`JwtAuthFilter`) checks validity:
   - Parses token
   - Loads `UserDetails`
   - Injects into `SecurityContext`

---

## 🧪 Testing

- Controller layer tested with `@WebMvcTest`  
- Service layer tested using `@MockBean`  
- Security filter mocked for test isolation

Run tests:
```bash
mvn test
```

---

## 🔮 Coming Soon (SmartBalaramAI)

- 💡 EMI Warning Module (JWT integrated) ✅  
- 📊 Financial Planning  
- 👨‍⚖️ Legal AI Assistant  
- 🛡️ Robo Defender (Fraud Protection)  
- And more...

---

## 🤝 Contact

**SmartBalaramAI by Balaram**  
## 🤝 Contribution

Open for PRs, feature requests, or issues.
Contact: Balaram - mailto: smartbalaram.ai@gmail.com 
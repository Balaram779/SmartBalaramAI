
# SmartBalaramAI - JWT Auth & EMI Warning Services

## 📦 auth-service

Handles authentication and authorization using Spring Security and JWT.

### 🔑 Features
- Register a user (USER or ADMIN)
- Login and receive a JWT token
- Role-based authorization for USER and ADMIN
- Secure endpoints using JWT

### 🔧 Endpoints
- `POST /api/auth/register` → Register new user
- `POST /api/auth/login` → Login and get JWT token
- `GET /api/user/hello` → Only USER role
- `GET /api/admin/hello` → Only ADMIN role
- `GET /api/common/hello` → Any authenticated user

### ✅ Role Validation Example
- USER cannot access `/api/admin/hello` (403 expected)
- ADMIN can access both `/admin` and `/user` endpoints

## 📦 emi-warning-service

Provides EMI risk analysis based on income and EMI burden. Secured via JWT.

### 🔧 Endpoints
- `POST /api/emi/warn` → Evaluate EMI and return risk level
- `GET /api/emi/cap/{monthlyIncome}` → Get recommended EMI cap
- `GET /api/emi/risk/{percentage}` → Get risk level
- `GET /api/emi/thresholds` → Risk threshold details
- `GET /api/emi/ping` → Health check

### 🔐 Security
All endpoints are protected by Spring Security + JWT. Token must be passed as `Authorization: Bearer <token>` header.

## 🔁 Integration Summary
1. `auth-service` issues JWT tokens after successful login.
2. `emi-warning-service` requires the JWT token for access.
3. JWTAuthFilter validates tokens in all requests.

## 🤝 Contribution

Open for PRs, feature requests, or issues.
Contact: Balaram - mailto: smartbalaram.ai@gmail.com 
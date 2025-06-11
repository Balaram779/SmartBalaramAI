# 📘 SmartBalaramAI – emi-warning-service

## 🧠 Overview

The `emi-warning-service` is a core microservice in the **SmartBalaramAI** ecosystem. It evaluates the risk level of a user's EMI (Equated Monthly Installment) burden based on income, loan tenure, and EMI values. Based on predefined thresholds, it flags whether the EMI load is risky and issues warnings accordingly.

## 🚀 Key Features

* Evaluate EMI burden and return risk level (Low, Medium, High)
* Auto-generate warnings if EMI is unsafe
* REST and GraphQL endpoints
* Kafka integration to publish evaluated EMI risk messages
* MongoDB for persistent EMI records
* Unit tests using JUnit + Mockito
* Traceable structured logs (for every request)
* Dockerized setup

---

## 🏗 Tech Stack

| Layer      | Technology             |
| ---------- | ---------------------- |
| Language   | Java 17                |
| Framework  | Spring Boot 3          |
| Build Tool | Maven                  |
| Messaging  | Apache Kafka           |
| DB         | MongoDB                |
| API Docs   | Swagger / GraphQL      |
| Tests      | JUnit + Mockito        |
| DevOps     | Docker, Docker Compose |

---

## 🧪 Sample REST APIs

### `POST /api/emi/evaluate`

Evaluate EMI and return risk info.

### `POST /api/emi/save`

Persist EMI record and get risk info.

### `GET /api/emi/all`

Fetch all EMI records.

### `GET /api/emi/{userId}`

Fetch specific EMI by userId.

### `DELETE /api/emi/{userId}`

Delete specific EMI record.

---

## 🧪 Sample GraphQL Queries

### Evaluate EMI

```graphql
mutation {
  evaluateEmi(input: {
    userId: "user789"
    monthlyIncome: 50000
    totalEmiAmount: 25000
    missedEmiCount: 1
    loanTenureMonths: 24
  }) {
    riskLevel
    warning
    emiPercentage
  }
}
```

### Get EMI by userId

```graphql
query {
  getEmiById(userId: "user789") {
    userId
    totalEmiAmount
    warning
  }
}
```

---

## 📤 Kafka Integration

* **Topic Name:** `emi-warning-topic`
* **Produced Message Format:**

```json
{
  "userId": "user789",
  "monthlyIncome": 50000,
  "totalEmiAmount": 25000,
  "warning": true,
  "emiPercentage": 50.0,
  "riskLevel": "HIGH",
  "suggestedMaxEmi": 15000.0
}
```

---

## 🧪 Unit Tests

* `EmiWarningServiceTest`

  * Mocked repository & Kafka
  * Coverage >80%

Run with:

```bash
mvn test
```

Jacoco report available at:
`target/site/jacoco/index.html`

---

## 🐳 Docker Setup

### Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/emi-warning-service.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### docker-compose.yml (sample)

```yaml
version: '3.8'
services:
  mongo:
    image: mongo:latest
    ports:
      - "27017:27017"
  kafka:
    image: bitnami/kafka:latest
    ports:
      - "9092:9092"
  emi-warning-service:
    build: .
    ports:
      - "8082:8082"
    depends_on:
      - mongo
      - kafka
```

---

## 📈 Sample MongoDB Document

```json
{
  "userId": "user789",
  "monthlyIncome": 50000,
  "totalEmiAmount": 25000,
  "missedEmiCount": 1,
  "loanTenureMonths": 24,
  "warning": true
}
```

---

## 🧾 Logs with Trace ID

Every request includes:

```text
TRACE_ID=213jfa-234jf2-2kkks
Incoming Request: POST /api/emi/save
UserID: user789
EmiPercentage: 50.0, Risk: HIGH, Warning: true
```

---

## 📦 Run Locally

```bash
git clone https://github.com/Balaram779/SmartBalaramAI.git
cd emi-warning-service
mvn clean install
java -jar target/emi-warning-service.jar
```

GraphQL: `http://localhost:8082/graphql`

Swagger (if enabled): `http://localhost:8082/swagger-ui/index.html`

---

## 🤝 Contribution

Open for PRs, feature requests, or issues.
Contact: [Balaram](mailto:balaram.dev.ai@gmail.com)

---

## 🧠 Powered By

**CP-Bala779** – Your AI teammate for life 🚀

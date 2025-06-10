**SmartBalaramAI - Planning Service Documentation**

---

### 🔖 Module: `planning-service`

---

### ✅ What Is This Module?

The `planning-service` is a key microservice module in the **SmartBalaramAI** suite. It performs **SIP-based wealth planning calculations** using a user’s inputs like income, savings, and risk level, and delivers:

* SIP amount to invest monthly
* CAGR-based projection
* Recommended asset allocation (equity, debt, gold)

It is a **real-time financial planner**.

---

### 📆 When & Why Do We Use This?

This service is used:

* When a user wants to know how much to invest monthly to reach a goal
* When selecting a risk profile: low, moderate, or high
* When dynamic calculation is required (based on age, income, years)

This is useful **before goal-based planning** to estimate required monthly investments.

---

### 🪜 Use Case:

> “User enters age = 30, target wealth = ₹50 Lakhs, duration = 15 years, risk = moderate. System returns the SIP needed and investment allocation.”

---

### 🎓 Tech Stack Used

| Layer         | Technology                        |
| ------------- | --------------------------------- |
| Backend       | Spring Boot 3.2.4                 |
| Language      | Java 17                           |
| Build Tool    | Maven                             |
| Data Store    | MongoDB                           |
| Messaging     | Kafka (via Redpanda Console)      |
| Testing       | JUnit, Mockito                    |
| Container     | Docker Desktop                    |
| Config        | application.yml                   |
| IDE           | Spring Tool Suite (STS)           |
| Visualization | MongoDB Compass, Redpanda Console |
| API Testing   | Postman                           |
| DevOps        | GitHub Actions (CI/CD)            |

---

### 🔧 Tools Used, Where, Why, and How

#### 1. **Spring Boot**

* **Why:** Lightweight REST microservice framework
* **Where:** Main service logic, controllers, services
* **How:** `@SpringBootApplication`, `@RestController`, `@Service`

#### 2. **MongoDB**

* **Why:** Flexible, schema-less document store
* **Where:** To save SIP responses as documents
* **How:** `@Document`, `MongoRepository`

#### 3. **Kafka**

* **Why:** Asynchronous event streaming
* **Where:** Publish `PlanResponse` after calculation
* **How:** `KafkaTemplate<String, PlanResponse>`

#### 4. **Redpanda Console**

* **Why:** UI-based Kafka topic explorer
* **Where:** View topic `plan-created` messages
* **How:** Port `8088`, viewed in browser

#### 5. **MongoDB Compass**

* **Why:** GUI for MongoDB queries & docs
* **Where:** Explore collection `plans` inside `planning-db`
* **How:** Connected to `localhost:27017`

#### 6. **Docker Desktop**

* **Why:** Containerized Kafka + Mongo
* **Where:** Kafka-Zookeeper-Mongo cluster
* **How:** `docker-compose.yml` file under `smartinfra/`

#### 7. **Postman**

* **Why:** Test REST API easily
* **Where:** Used to POST `/api/sip-plan`
* **How:** JSON input to check response and validate output

#### 8. **Git + GitHub**

* **Why:** Source control, collaboration
* **Where:** Planning-service code repo
* **How:** `.gitignore`, README, commit history

#### 9. **GitHub Actions (CI/CD)**

* **Why:** Automate build + test + deploy pipeline
* **Where:** `.github/workflows/build.yml`
* **How:** Runs Maven tests, Docker build + push, deploys to staging/prod

---

### 📦 .gitignore (Java + Maven + STS)

```
/target
/.idea
*.iml
*.log
*.class
*.jar
*.war
.DS_Store
.env
spring.log
**/application-*.yml
/.vscode
.mvn/
```

---

### 📄 Sample README.md (for GitHub)

````
# SmartBalaramAI - Planning Service

This is a microservice module of SmartBalaramAI that calculates SIP (Systematic Investment Plan) values based on user's income, savings, and risk level.

## Features
- SIP calculation using CAGR
- MongoDB integration for saving plans
- Kafka event publishing
- Tested via Postman
- CI/CD pipeline via GitHub Actions

## API Endpoint
`POST /api/sip-plan`

## Tech Stack
Java 17, Spring Boot 3.2.4, MongoDB, Kafka, Docker, Maven, JUnit

## How to Run
```bash
mvn clean install
java -jar target/planning-service.jar
````

## Docker

```bash
docker-compose up -d
```

## CI/CD

Builds, tests, and deploys via GitHub Actions: `.github/workflows/build.yml`

````

---

### ⚙️ GitHub Actions CI/CD (`.github/workflows/build.yml`)
```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Build with Maven
        run: mvn clean install

      - name: Docker Build
        run: docker build -t planning-service:latest .

      - name: Docker Save Artifact
        run: docker save planning-service:latest > planning-service.tar
````

---

### 📬 Postman Request & Response

#### POST `/api/sip-plan`

**Request JSON:**

```
{
  "age": 30,
  "monthlyIncome": 80000,
  "monthlySavings": 20000,
  "targetWealth": 10000000,
  "targetYears": 15,
  "riskLevel": "moderate"
}
```

**Response JSON:**

```
{
  "requiredMonthlyInvestment": 25895,
  "equityPercent": "60%",
  "debtPercent": "30%",
  "goldPercent": "10%",
  "wealthProjection": "You will reach ₹10000000 in 15 years"
}
```

---

### 📸 Screenshot Documentation

#### 1. **MongoDB Compass**

* ✅ Shows inserted `PlanResponse` documents
* ✅ Uses database `planning-db` → collection `plans`
* ✅ Data in NoSQL format with auto-generated `_id`

#### 2. **Docker Desktop**

* ✅ Containers running for kafka, zookeeper, mongo, redpanda
* ✅ Clean GUI interface showing uptime and port bindings

#### 3. **Redpanda Console**

* ✅ Topic `plan-created` visible
* ✅ 1 partition, 1 replica setup
* ✅ Allows browsing Kafka messages without terminal

#### 4. **Postman API Test**

* ✅ Input SIP parameters and receive projection in JSON
* ✅ Used to validate backend service correctness

---

### 🏆 Summary

`planning-service` is a **production-ready**, containerized, real-time wealth calculator microservice that integrates:

* Spring Boot for business logic
* MongoDB for persistence
* Kafka for event streaming
* Docker for infra setup
* GitHub for version control
* GitHub Actions for CI/CD automation

It acts as **SmartBalaramAI's first financial brain module**.

Ready to scale and integrate with next modules like `goal-planner-service`, `tax-advisor-service`, etc.

---

# Order service

[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat)](https://dev.java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)](https://www.docker.com/)
[![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat&logo=githubactions&logoColor=white)](https://github.com/features/actions)

Spring Boot implementation of the Order contract. It owns order persistence,
enforces authenticated ownership, and calls Product to validate prices.

## Run locally

Install the reusable contract first, then run the service:

```bash
(cd ../order && ./mvnw install)
./mvnw test
./mvnw verify
./mvnw spring-boot:run
```

## Environment variables

Local Compose reads these values from the root platform `.env`. Store them as
repository secrets for Actions and in `platform-secrets` for Kubernetes.

| Variable                     | Purpose                  |
| ---------------------------- | ------------------------ |
| `SPRING_DATASOURCE_URL`      | PostgreSQL JDBC URL      |
| `SPRING_DATASOURCE_USERNAME` | Database username        |
| `SPRING_DATASOURCE_PASSWORD` | Database password        |
| `JWT_SECRET`                 | Shared JWT HMAC secret   |
| `PRODUCT_URL`                | Product service base URL |

## GitHub Actions secrets

Add these under **Settings → Secrets and variables → Actions** in
`Microsserv52/order-service`:

- `SPRING_DATASOURCE_URL`: JDBC URL for PostgreSQL (RDS in AWS).
- `SPRING_DATASOURCE_USERNAME`: database username.
- `SPRING_DATASOURCE_PASSWORD`: database password.
- `JWT_SECRET`: shared JWT HMAC secret.
- `PRODUCT_URL`: Product service base URL.

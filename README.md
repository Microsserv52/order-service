# Order service

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

| Variable | Purpose |
| --- | --- |
| `SPRING_DATASOURCE_URL` | PostgreSQL JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `JWT_SECRET` | Shared JWT HMAC secret |
| `PRODUCT_URL` | Product service base URL |

## GitHub Actions secrets

Add these under **Settings → Secrets and variables → Actions** in
`Microsserv52/order-service`:

- `SPRING_DATASOURCE_URL`: JDBC URL for PostgreSQL (RDS in AWS).
- `SPRING_DATASOURCE_USERNAME`: database username.
- `SPRING_DATASOURCE_PASSWORD`: database password.
- `JWT_SECRET`: shared JWT HMAC secret.
- `PRODUCT_URL`: Product service base URL.

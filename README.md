# Exception Handling Platform

This repository demonstrates a reusable exception-handling stack for Java/Spring applications. It contains:

- **exception-core** – a lightweight library defining `ErrorCode`, `AppException`, and the standard `ErrorResponse` DTO.
- **exception-i18n-spring-boot-starter** – a Spring Boot starter that localizes `AppException` messages and exposes a global REST exception handler out of the box.
- **demo-exception-app** – a sample Spring Boot service showing how to use the starter with domain-specific errors and localized messages.
- **infrastructure** – helper assets such as a Docker Compose file for running a local Nexus repository used in the module POMs.

The goal is to provide consistent, localized error responses across microservices with minimal boilerplate. You can cherry-pick the modules you need or run the demo end-to-end.

## Repository layout

| Path | Description |
| --- | --- |
| `exception-core/` | Core error abstractions (`ErrorCode`, `AppException`, `ErrorResponse`). |
| `exception-i18n-spring-boot-starter/` | Auto-configuration that turns `AppException` into localized HTTP responses. |
| `demo-exception-app/` | REST API demonstrating usage with custom error codes and translations. |
| `infrastructure/docker-compose.yml` | Optional Nexus instance for publishing artifacts. |

## Quick start

1. **Build all modules** (Java 17+):
   ```bash
   mvn -pl exception-core,exception-i18n-spring-boot-starter,demo-exception-app -am clean package
   ```
2. **Run the demo service** (from `demo-exception-app`):
   ```bash
   mvn spring-boot:run
   ```
3. Open `http://localhost:8080/swagger-ui.html` to explore the API and trigger sample errors.

## Error handling flow

1. Application code throws `AppException` with an `ErrorCode` implementation and optional arguments.
2. The starter's `GlobalExceptionHandler` resolves a localized message (using `MessageSource` and request locale) and builds an `ErrorResponse`.
3. Responses include a timestamp, error code, message, request path, and a trace ID for correlation.
4. You can override beans such as `ErrorResponseCustomizer`, `MessageSource`, and `LocaleResolver` to fit your service.

## Internationalization

- Messages are looked up by `ErrorCode#code()` in `messages*.properties` files.
- The default locale is configurable via `exception.i18n.default-locale` (see the starter README).
- When a translation is missing, the resolver falls back to `ErrorCode#defaultMessage()` or, optionally, the code itself.

## Tests

Run the demo application's test suite:
```bash
cd demo-exception-app
mvn test
```

## Publishing artifacts

If you need a local Maven repository, start Nexus via Docker Compose (optional):
```bash
cd infrastructure
docker compose up -d
```
Configure your Maven settings to deploy to `http://localhost:8081/repository/maven-releases/` or `maven-snapshots/` as referenced in the module POMs.

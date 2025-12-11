# demo-exception-app

Sample Spring Boot REST service showcasing the exception-handling stack with internationalized messages.

## Running the app

```bash
mvn spring-boot:run
```
Then open Swagger UI at `http://localhost:8080/swagger-ui.html` to explore endpoints.

## API overview

- **GET `/api/users/{id}`** — returns the user's email; throws `USER_NOT_FOUND` when missing.
- **POST `/api/users/{id}?email=`** — creates a user; throws `USER_ALREADY_EXISTS` if the ID is taken or `INVALID_EMAIL` when the format is wrong.

Responses on errors follow the shared `ErrorResponse` shape:
```json
{
  "timestamp": "2024-05-01T12:00:00Z",
  "errorCode": "USER_NOT_FOUND",
  "message": "User with id 99 not found",
  "path": "/api/users/99",
  "traceId": "abc123"
}
```

## Internationalization

Message bundles live in `src/main/resources/messages*.properties`:
- `messages.properties` (default English)
- `messages_uz.properties` (Uzbek)
- `messages_ru.properties` (Russian)

The locale is resolved from the `Accept-Language` header by default (configurable). Example header: `Accept-Language: uz`.

## Customization examples

- **`CustomErrorResponseCustomizer`** — wraps `ErrorResponse.builder()` so you can add metadata before returning to clients.
- **`CustomLocaleResolverConfig`** — sets a default locale and allows header-based resolution.
- **`CustomMessageSourceConfig`** — exposes a `MessageSource` bean pointing at the message bundles with UTF-8 encoding and quick reload for development.
- **`CustomGlobalExceptionHandler`** (commented out) — shows how to replace the starter's handler entirely (e.g., to return a fixed HTTP status for demos).

## Dependencies

Key dependencies:
- `exception-core` and `exception-i18n-spring-boot-starter` (local modules)
- `spring-boot-starter-web`
- `springdoc-openapi-starter-webmvc-ui` for Swagger UI

Java version: 17+

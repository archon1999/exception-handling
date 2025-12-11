# exception-i18n-spring-boot-starter

Spring Boot starter that provides internationalized, structured error responses for services using `exception-core`.

## Features

- Global `@RestControllerAdvice` that converts `AppException` into an `ErrorResponse` with HTTP status derived from the `ErrorCode`.
- Locale-aware message resolution using Spring `MessageSource`, including graceful fallbacks.
- Pluggable beans for message resolution, locale resolution, and response customization.
- Sensible defaults with override points so you can adapt it per service.

## How it works

1. A controller or service throws `AppException` with an `ErrorCode` and optional placeholder arguments.
2. `GlobalExceptionHandler` resolves the locale (defaulting to `Accept-Language` via `LocaleResolver`) and looks up the message with `MessageResolver`.
3. The resolved text, error code, request path, timestamp, and a generated trace ID are passed to `ErrorResponseCustomizer` to build the HTTP payload.
4. The handler returns a `ResponseEntity` using the `httpStatus()` provided by the `ErrorCode` (fallback: 500 when missing/unknown).

## Configuration

Properties prefix: `exception.i18n.*`

| Property | Default | Description |
| --- | --- | --- |
| `exception.i18n.base-names` | `["messages"]` | Message bundle base names used by the `MessageSource`. |
| `exception.i18n.default-locale` | `en` | Locale used when none is resolved from the request. |
| `exception.i18n.show-code-when-missing` | `true` | Whether to fall back to the error code if a translation is missing and no default message is provided. |

Place property values in `application.yml` or `application.properties` of your service. Example:
```yaml
exception:
  i18n:
    base-names: ["messages", "errors"]
    default-locale: en
    show-code-when-missing: false
```

## Bean overrides

You can override any of these beans by defining your own bean of the same type:

- `MessageSource` — customize bundle locations, cache settings, etc.
- `MessageResolver` — change resolution rules.
- `ErrorResponseCustomizer` — augment the error payload (e.g., add service name or extra metadata).
- `LocaleResolver` — switch to a session/cookie resolver if desired.
- `GlobalExceptionHandler` — replace the entire handler.

## Dependency coordinates

Add the starter alongside `exception-core` in your Spring Boot application:
```xml
<dependency>
  <groupId>uz.xhamdam</groupId>
  <artifactId>exception-core</artifactId>
  <version>0.0.2</version>
</dependency>
<dependency>
  <groupId>uz.xhamdam</groupId>
  <artifactId>exception-i18n-spring-boot-starter</artifactId>
  <version>0.0.2</version>
</dependency>
```

The starter targets Java 17+ and Spring Boot 3/4 servlet applications (auto-configuration is conditional on a servlet web app).

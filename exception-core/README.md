# exception-core

A lightweight library that standardizes how services describe and expose errors. It is framework-agnostic and ships only the abstractions needed to represent errors consistently.

## What it provides

- **`ErrorCode` interface** — contract for domain-specific error enums (stable code, HTTP status, optional default message).
- **`AppException`** — runtime exception carrying an `ErrorCode` and optional message arguments for localization placeholders.
- **`ErrorResponse`** — immutable DTO representing the payload returned to clients (timestamp, code, message, path, trace ID).
- **`ExampleErrorCodes`** — simple sample enum for reference/testing.

## Usage

1. **Define your error codes** by implementing `ErrorCode` (usually an enum):
   ```java
   public enum UserErrors implements ErrorCode {
     USER_NOT_FOUND(404, "User not found"),
     USER_ALREADY_EXISTS(400, "User already exists");

     private final int status;
     private final String defaultMessage;

     UserErrors(int status, String defaultMessage) {
       this.status = status;
       this.defaultMessage = defaultMessage;
     }

     public String code() { return name(); }
     public int httpStatus() { return status; }
     public String defaultMessage() { return defaultMessage; }
   }
   ```

2. **Throw `AppException`** from your business logic:
   ```java
   throw new AppException(UserErrors.USER_NOT_FOUND, userId);
   ```
   The optional varargs are preserved for `MessageFormat`-style placeholders when resolving localized text.

3. **Build responses** using `ErrorResponse` directly or via helper layers:
   ```java
   ErrorResponse payload = ErrorResponse.builder()
       .timestampNow()
       .errorCode(error.code())
       .message("Friendly message")
       .path("/api/users/1")
       .traceId("abc-123")
       .build();
   ```

## Packaging

- Maven coordinates: `uz.xhamdam:exception-core:0.0.2`
- Java version: 17+
- No Spring dependencies; the module depends only on `jackson-annotations` for optional JSON serialization hints.

## Publishing

POM includes distribution management entries for a local Nexus at `http://localhost:8081/`. Adjust or override in your own Maven settings as needed.

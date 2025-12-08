package uz.xhamdam;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Standard error response DTO returned to clients.
 *
 * Fields:
 * - timestamp: ISO-8601 instant
 * - errorCode: stable error code string
 * - message: user-facing message (i18n resolved)
 * - path: HTTP request path (to be filled by web layer)
 * - traceId: optional correlation id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ErrorResponse {

  private final String timestamp;
  private final String errorCode;
  private final String message;
  private final String path;
  private final String traceId;

  private ErrorResponse(String timestamp, String errorCode, String message, String path, String traceId) {
    this.timestamp = timestamp;
    this.errorCode = errorCode;
    this.message = message;
    this.path = path;
    this.traceId = traceId;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }

  public String getTraceId() {
    return traceId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ErrorResponse of(ErrorCode code, String message, String path, String traceId) {
    return builder()
        .errorCode(code == null ? null : code.code())
        .message(message)
        .path(path)
        .traceId(traceId)
        .build();
  }

  public static final class Builder {
    private String timestamp;
    private String errorCode;
    private String message;
    private String path;
    private String traceId;

    private Builder() { }

    public Builder timestampInstant(Instant instant) {
      this.timestamp = DateTimeFormatter.ISO_INSTANT.format(Objects.requireNonNull(instant));
      return this;
    }

    public Builder timestampNow() {
      this.timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
      return this;
    }

    public Builder errorCode(String errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public Builder path(String path) {
      this.path = path;
      return this;
    }

    public Builder traceId(String traceId) {
      this.traceId = traceId;
      return this;
    }

    public ErrorResponse build() {
      if (this.timestamp == null) {
        timestampNow();
      }
      return new ErrorResponse(timestamp, errorCode, message, path, traceId);
    }
  }

  @Override
  public String toString() {
    return "ErrorResponse{" +
        "timestamp='" + timestamp + '\'' +
        ", errorCode='" + errorCode + '\'' +
        ", message='" + message + '\'' +
        ", path='" + path + '\'' +
        ", traceId='" + traceId + '\'' +
        '}';
  }

}

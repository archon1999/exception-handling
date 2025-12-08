package uz.xhamdam;

/**
 * Simple contract for error codes used across services.
 * <p>
 * Implementations should return a stable unique code and a HTTP status code integer
 * (so the core module has no dependency on Spring's HttpStatus).
 */
public interface ErrorCode {

  String code();

  /**
   * HTTP status code to use when exposing this error (e.g. 404, 400, 500).
   * Use standard HTTP status numbers to avoid depending on Spring in core.
   */
  int httpStatus();

  /**
   * Optional short default message used as a fallback when a localized message isn't available.
   */
  default String defaultMessage() {
    return code();
  }
}

package uz.xhamdam.exception.i18n;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.LocaleResolver;
import uz.xhamdam.AppException;
import uz.xhamdam.ErrorCode;
import uz.xhamdam.ErrorResponse;

/**
 * Global exception handler converting AppException -> ErrorResponse (i18n).
 * Can be overridden by defining your own bean of the same type.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private final MessageResolver messageResolver;
  private final ErrorResponseCustomizer customizer;
  private final LocaleResolver localeResolver;

  public GlobalExceptionHandler(MessageResolver messageResolver,
      ErrorResponseCustomizer customizer,
      LocaleResolver localeResolver) {
    this.messageResolver = messageResolver;
    this.customizer = customizer;
    this.localeResolver = localeResolver;
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ErrorResponse> handleAppException(AppException ex, HttpServletRequest request) {
    ErrorCode code = ex.getErrorCode();
    Locale locale = Optional.ofNullable(localeResolver).map(lr -> lr.resolveLocale(request)).orElse(Locale.getDefault());
    String message = messageResolver.resolve(code, ex.getArgs(), locale);
    String traceId = extractOrCreateTraceId(request);

    ErrorResponse payload = customizer.customize(code, message, request, traceId);

    int statusCode = code == null ? 500 : code.httpStatus();
    org.springframework.http.HttpStatus status = org.springframework.http.HttpStatus.resolve(statusCode);
    if (status == null) {
      status = org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
    }

    // logging
    if (status.is5xxServerError()) {
      log.error("AppException handled: code={} path={} traceId={}", code == null ? "null" : code.code(), request.getRequestURI(), traceId, ex);
    } else {
      log.info("AppException handled: code={} path={} traceId={}", code == null ? "null" : code.code(), request.getRequestURI(), traceId);
    }

    return ResponseEntity.status(status).body(payload);
  }

  // Fallback handler for other exceptions if you want (optional)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleOther(Exception ex, HttpServletRequest request) {
    String traceId = extractOrCreateTraceId(request);
    ErrorResponse payload = customizer.customize(null, ex.getMessage(), request, traceId);
    log.error("Unhandled exception: path={} traceId={}", request.getRequestURI(), traceId, ex);
    return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).body(payload);
  }

  private String extractOrCreateTraceId(HttpServletRequest request) {
    String traceId = request.getHeader("X-Trace-Id");
    if (StringUtils.hasText(traceId)) return traceId;
    return UUID.randomUUID().toString();
  }
}

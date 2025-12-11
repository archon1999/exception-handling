package uz.xhamdam.demoexceptionapp;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.xhamdam.AppException;
import uz.xhamdam.ErrorResponse;
import uz.xhamdam.exception.i18n.ErrorResponseCustomizer;

//@RestControllerAdvice
public class CustomGlobalExceptionHandler {

  private final ErrorResponseCustomizer customizer;

  public CustomGlobalExceptionHandler(ErrorResponseCustomizer customizer) {
    this.customizer = customizer;
  }

//  @ExceptionHandler(AppException.class)
  public ResponseEntity<ErrorResponse> handle(AppException ex, HttpServletRequest req) {
    // Example: always return 418 for demo
    ErrorResponse res = customizer.customize(ex.getErrorCode(), ex.getMessage(), req, "trace-demo");
    return ResponseEntity.status(418).body(res);
  }
}

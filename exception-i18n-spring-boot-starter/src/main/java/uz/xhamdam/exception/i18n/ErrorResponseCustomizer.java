package uz.xhamdam.exception.i18n;

import jakarta.servlet.http.HttpServletRequest;
import uz.xhamdam.ErrorCode;
import uz.xhamdam.ErrorResponse;

public interface ErrorResponseCustomizer {

  ErrorResponse customize(ErrorCode code,
      String message,
      HttpServletRequest request,
      String traceId);
}

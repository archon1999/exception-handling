package uz.xhamdam.demoexceptionapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.xhamdam.ErrorResponse;
import uz.xhamdam.exception.i18n.ErrorResponseCustomizer;

@Configuration
public class CustomErrorResponseCustomizer {

  @Bean
  public ErrorResponseCustomizer customizer() {
    return (code, message, request, traceId) -> {
      // add "service" metadata into response by wrapping/using builder
      return ErrorResponse.builder()
          .timestampNow()
          .errorCode(code == null ? null : code.code())
          .message(message)
          .path(request == null ? null : request.getRequestURI())
          .traceId(traceId)
          .build();
    };
  }
}

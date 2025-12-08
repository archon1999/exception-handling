package uz.xhamdam.exception.i18n;

import java.util.Locale;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import uz.xhamdam.ErrorResponse;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(ExceptionProperties.class)
public class ExceptionI18nAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public MessageSource exceptionMessageSource(ExceptionProperties properties) {
    ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasenames(properties.getBaseNames().toArray(new String[0]));
    ms.setDefaultEncoding("UTF-8");
    ms.setFallbackToSystemLocale(false);
    ms.setUseCodeAsDefaultMessage(false);
    return ms;
  }

  @Bean
  @ConditionalOnMissingBean
  public MessageResolver messageResolver(MessageSource messageSource,
      ExceptionProperties properties) {
    return new MessageResolver(messageSource, properties);
  }

  @Bean
  @ConditionalOnMissingBean
  public ErrorResponseCustomizer errorResponseCustomizer() {
    return (code, message, request, traceId) -> ErrorResponse.builder()
        .errorCode(code == null ? null : code.code())
        .message(message)
        .path(request == null ? null : request.getRequestURI())
        .traceId(traceId)
        .timestampNow()
        .build();
  }

  @Bean
  @ConditionalOnMissingBean
  public GlobalExceptionHandler globalExceptionHandler(MessageResolver resolver,
      ErrorResponseCustomizer customizer,
      LocaleResolver localeResolver) {
    return new GlobalExceptionHandler(resolver, customizer, localeResolver);
  }

  @Bean
  @ConditionalOnMissingBean
  public LocaleResolver exceptionLocaleResolver(ExceptionProperties properties) {
    // Use AcceptHeaderLocaleResolver by default
    org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver l = new org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver();
    l.setDefaultLocale(
        properties.getDefaultLocale() == null ? Locale.ENGLISH : properties.getDefaultLocale());
    return l;
  }
}

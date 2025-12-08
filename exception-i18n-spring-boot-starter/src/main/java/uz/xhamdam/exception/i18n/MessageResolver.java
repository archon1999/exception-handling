package uz.xhamdam.exception.i18n;

import jakarta.annotation.Nullable;
import java.util.Locale;
import org.springframework.context.MessageSource;
import uz.xhamdam.ErrorCode;

public class MessageResolver {

  private final MessageSource messageSource;
  private final ExceptionProperties properties;

  public MessageResolver(MessageSource messageSource, ExceptionProperties properties) {
    this.messageSource = messageSource;
    this.properties = properties;
  }

  /**
   * Resolved message for provided ErrorCode and args using given locale. Falls back gracefully.
   */
  public String resolve(ErrorCode errorCode, @Nullable Object[] args, Locale locale) {
    if (errorCode == null) {
      return "";
    }
    Locale useLocale = locale == null ? properties.getDefaultLocale() : locale;
    String code = errorCode.code();
    try {
      String resolved = messageSource.getMessage(code, args, null, useLocale);
      if (resolved != null) {
        return resolved;
      }
    } catch (Exception ignored) {
      // swallow and fallback
    }

    // fallback to defaultMessage or code based on property
    String defaultMsg = errorCode.defaultMessage();
    if (defaultMsg != null && !defaultMsg.isBlank()) {
      return defaultMsg;
    }
    return properties.isShowCodeWhenMissing() ? code : "";
  }
}

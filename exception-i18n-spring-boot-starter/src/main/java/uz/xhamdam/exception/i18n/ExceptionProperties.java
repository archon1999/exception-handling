package uz.xhamdam.exception.i18n;

import java.util.List;
import java.util.Locale;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exception.i18n")
public class ExceptionProperties {

  /**
   * Base names for MessageSource (spring ResourceBundleMessageSource).
   * Default: messages
   */
  private List<String> baseNames = List.of("messages");

  /**
   * Default locale when none is provided.
   */
  private Locale defaultLocale = Locale.ENGLISH;

  /**
   * Whether to include the error code inside returned message if message not found.
   */
  private boolean showCodeWhenMissing = true;

  public List<String> getBaseNames() {
    return baseNames;
  }

  public void setBaseNames(List<String> baseNames) {
    this.baseNames = baseNames;
  }

  public Locale getDefaultLocale() {
    return defaultLocale;
  }

  public void setDefaultLocale(Locale defaultLocale) {
    this.defaultLocale = defaultLocale;
  }

  public boolean isShowCodeWhenMissing() {
    return showCodeWhenMissing;
  }

  public void setShowCodeWhenMissing(boolean showCodeWhenMissing) {
    this.showCodeWhenMissing = showCodeWhenMissing;
  }
}

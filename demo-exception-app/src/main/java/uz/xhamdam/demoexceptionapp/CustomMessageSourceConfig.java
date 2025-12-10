package uz.xhamdam.demoexceptionapp;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class CustomMessageSourceConfig {

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("classpath:messages"); // same base name used by starter
    ms.setDefaultEncoding("UTF-8");
    ms.setCacheSeconds(5); // for dev: allow reload without restarting
    return ms;
  }
}

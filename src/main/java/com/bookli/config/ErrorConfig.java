package com.bookli.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@PropertySource("classpath:static/errors.properties")
public class ErrorConfig {

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:static/errors");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}

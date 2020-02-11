package com.my.pl.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemProperties {
  public SystemProperties() {
    System.setProperty("org.jooq.no-logo", "true");
  }
}

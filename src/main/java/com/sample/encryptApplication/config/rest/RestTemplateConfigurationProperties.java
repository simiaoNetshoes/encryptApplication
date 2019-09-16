package com.sample.encryptApplication.config.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "restTemplate")
public class RestTemplateConfigurationProperties {

  private Integer maxTotal;
  private Integer maxPerRoute;
  private Integer keepAliveTimeMillis;
  private Integer connectionTimeout;
  private Integer requestTimeout;
  private Integer socketTimeout;
}

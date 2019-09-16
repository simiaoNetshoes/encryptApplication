package com.sample.encryptApplication.config.feign;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("netshoes.inkaCorp.oauth2")
public class FbTokenInkaFeignConfiguration {

  private String grantType;
  private String scope;
  private String username;
  private String password;
  private String url;
}

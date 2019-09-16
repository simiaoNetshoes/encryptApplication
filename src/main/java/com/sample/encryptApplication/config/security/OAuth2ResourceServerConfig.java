package com.sample.encryptApplication.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and()
        .authorizeRequests()
//          .antMatchers("/swagger-ui.html").permitAll()
//          .antMatchers("/swagger-resources").permitAll()
//          .antMatchers("/swagger-resources/**").permitAll()
//          .antMatchers("/v2/api-docs").permitAll()
//          .antMatchers("/paypal/**").access("#oauth2.isUser()")
//          .antMatchers("//").permitAll()
          .antMatchers("/**").permitAll();
    // @formatter:on
  }

  @Override
  public void configure(final ResourceServerSecurityConfigurer resources) {
    resources.resourceId("restservice");
  }
}
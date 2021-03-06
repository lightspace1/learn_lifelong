package com.lightspace.manage_course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


  private static final String PUBLIC_KEY = "publickey.txt";

  @Bean
  public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
    return new JwtTokenStore(jwtAccessTokenConverter);
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setVerifierKey(getPubKey());
    return converter;
  }

  private String getPubKey() {
    Resource resource = new ClassPathResource(PUBLIC_KEY);
    try {
      InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
      BufferedReader br = new BufferedReader(inputStreamReader);
      return br.lines().collect(Collectors.joining("\n"));
    } catch (IOException ioe) {
      return null;
    }
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
      .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui",
        "/swagger-resources","/swagger-resources/configuration/security",
        "/swagger-ui.html","/webjars/**").permitAll()
      .anyRequest().authenticated();
  }
}


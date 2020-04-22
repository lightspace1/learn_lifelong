package com.lightspace.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@EnableFeignClients
@EntityScan("com.lightspace.framework.domain.ucenter")
@ComponentScan(basePackages={"com.lightspace.api"})
@ComponentScan(basePackages={"com.lightspace.framework"})
@SpringBootApplication
public class UcenterAuthApplication {
  public static void main(String[] args) {
    SpringApplication.run(UcenterAuthApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
  }

}

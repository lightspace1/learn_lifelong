package com.lightspace.manage_media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.lightspace.framework.domain.media")
@ComponentScan(basePackages={"com.lightspace.api"})
@ComponentScan(basePackages={"com.lightspace.framework"})
@ComponentScan(basePackages={"com.lightspace.manage_media"})
public class ManageMediaApplication {
  public static void main(String[] args) {
    SpringApplication.run(ManageMediaApplication.class,args);
  }

  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
  }
}

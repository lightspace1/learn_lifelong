package com.lightspace.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(value={"com.lightspace.framework.domain.order","com.lightspace.framework.domain.task"})
@ComponentScan(basePackages={"com.lightspace.api"})
@ComponentScan(basePackages={"com.lightspace.framework"})
@ComponentScan(basePackages={"com.lightspace.order"})
@SpringBootApplication
public class ManageOrderApplication {
  public static void main(String[] args) throws Exception {
    SpringApplication.run(ManageOrderApplication.class, args);
  }

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
  }
}

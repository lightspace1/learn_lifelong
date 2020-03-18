package com.lightspace.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.lightspace.framework.domain.search")
@ComponentScan(basePackages={"com.lightspace.api"})
@ComponentScan(basePackages={"com.lightspace.search"})
@ComponentScan(basePackages={"com.lightspace.framework"})
public class SearchApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(SearchApplication.class, args);
  }

}

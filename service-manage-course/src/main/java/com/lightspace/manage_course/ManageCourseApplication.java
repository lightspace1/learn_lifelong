package com.lightspace.manage_course;

import com.lightspace.framework.intercepter.FeignClientInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.lightspace.framework.domain.course")
@ComponentScan(basePackages={"com.lightspace.api"})
@ComponentScan(basePackages={"com.lightspace.manage_course"})
@ComponentScan(basePackages={"com.lightspace.framework"})
public class ManageCourseApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ManageCourseApplication.class, args);
    }

  @Bean
  public FeignClientInterceptor getFeignClientInterceptor(){
    return new FeignClientInterceptor();
  }
}

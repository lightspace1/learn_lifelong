package com.lightspace.manage_media_process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.lightspace.framework.domain.media")
@ComponentScan(basePackages={"com.lightspace.api"})
@ComponentScan(basePackages={"com.lightspace.manage_media_process"})
@ComponentScan(basePackages={"com.lightspace.framework"})
public class ManageMediaProcessorApplication {
  public static void main(String[] args) {
    SpringApplication.run(ManageMediaProcessorApplication.class, args);
  }
}

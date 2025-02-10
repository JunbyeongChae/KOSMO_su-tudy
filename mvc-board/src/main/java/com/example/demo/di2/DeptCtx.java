package com.example.demo.di2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.demo.di2")
public class DeptCtx {
  @Bean
  public DeptController deptController() {
    return new DeptController();
  }
  @Bean
  public DeptService deptService() {
    return new DeptService();
  }
}

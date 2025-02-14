package com.example.demo.di3;

import org.springframework.context.annotation.Bean;

import com.example.demo.di1.Car;
import com.example.demo.di1.Door;
import com.example.demo.di1.Engine;

public class MyAppConfig {
  @Bean
  public Car car() {
    return new Car();
  }
  @Bean
  public Engine engine() {
    return new Engine();
  }
  @Bean
  public Door door() {
    return new Door();
  }
}

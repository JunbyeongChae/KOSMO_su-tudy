package com.example.demo.di1;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.Resource;
import lombok.Data;

@Data
public class Car {
  @Autowired
  Engine engine;
  @Resource
  Door door;
  @Override
  public String toString() {
    return "Car [engine=" + engine + ", door=" + door + "]";
  }
}

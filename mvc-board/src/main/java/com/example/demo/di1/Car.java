package com.example.demo.di1;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data
public class Car {
  @Autowired
  Engine engine;
  Door door;
  @Override
  public String toString() {
    return "Car [engine=" + engine + ", door=" + door + "]";
  }
}

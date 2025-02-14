package com.example.demo.di3;

import com.example.demo.di1.Car;
import com.example.demo.di1.Door;
import com.example.demo.di1.Engine;

public class MyAppConfigMain {
  public static void main(String[] args) {
    MyAppContext mac = new MyAppContext(MyAppConfig.class);
    Car car = (Car)mac.getBean("car");
    System.out.println("car:"+ car);
    Car car2 = (Car)mac.getBean(Car.class);
    System.out.println("car2:"+ car2);
    //bean들끼리의 관계를 설정
    Engine engine = (Engine)mac.getBean("engine");
    car.setEngine(engine);
    System.out.println("engine:"+ engine);
    Door door = (Door)mac.getBean("door");
    car.setDoor(door);
    System.out.println("door:"+ door);
  }
}

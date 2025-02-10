package com.example.demo.di3;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.example.demo.di1.Car;
import com.example.demo.di1.Engine;

public class ReflectionTest {
  public static void main(String[] args) throws Exception{
    Car car = new Car();
    //1. 객체로부터 Class객체 얻기
    Class carClass = car.getClass();
    carClass = Car.class;
    carClass = Class.forName("com.example.demo.di1.Car");
    System.out.println(car);
    Car car2 = (Car)carClass.newInstance();
    System.out.println(car==car2);

    //2.클래스에 선언된 멤버변수 (field)와 메서드 목록 얻기(상위클래스 메서드 목록)
    Field[] mvArr = carClass.getDeclaredFields();
    for(Field f:mvArr) {
      System.out.println(f.getName());
    }
    Method[] methArr = carClass.getMethods();
    for(Method m:methArr) {
      System.out.println(m.getName());
    }

    Method method = carClass.getMethod("setEngine", Engine.class);
    method.invoke(car, new Engine());
    System.out.println(method);
    System.out.println(car);

    //3.멤버변수에 set을 붙여서 setter호출하기
    for(Field mv:mvArr){
      System.out.println(mv);
      String methodName = "set"+StringUtils.capitalize(mv.getName());
      System.out.println(methodName);
    }

    //4.멤버변수에 @Autowired가 붙었는지 확인하기
    for(Field mv:mvArr){
      Annotation[] annArr = mv.getDeclaredAnnotations();
      for(Annotation ann:annArr){
        System.out.println(mv.getName());
        if (ann.annotationType()==Autowired.class){
          String methodName = "set"+StringUtils.capitalize(mv.getName());
          method = carClass.getMethod(methodName, mv.getType());
          System.out.println(method);
          method.invoke(car, mv.getType().newInstance());
        }
      }
    }
  }
}

package com.example.demo.di3;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.example.demo.di1.Door;
import com.example.demo.di1.Engine;
import com.example.demo.di1.SportsCar;

public class MyAppContext {
  Map<String, Object> map = new HashMap<>();
  MyAppContext() {
    map.put("car", new SportsCar());
    map.put("engine", new Engine());
    map.put("door", new Door());
  }

  public MyAppContext(Class<MyAppConfig> clazz) {
    try {
      Object config = clazz.getDeclaredConstructor().newInstance();
      Method[] methods = clazz.getDeclaredMethods();
      for(Method method : methods) {
        System.out.println("method:"+method.getName());
        for(Annotation ann : method.getDeclaredAnnotations()) {
          if(ann.annotationType() == Bean.class) {
            System.out.println("bean annotation");
            try {
              Object bean = method.invoke(config);
              map.put(method.getName(), bean);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    doAutowired();
  }
  private void doAutowired() {
    for(Object obj : map.values()) {
      for(Field field : obj.getClass().getDeclaredFields()) {
        if(field.getAnnotation(Autowired.class) != null) {
          System.out.println("autowired");
          try {
            field.setAccessible(true);
            field.set(obj, getBean(field.getType()));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
  public Object getBean(Class<?> clazz){
    for(Object obj : map.values()) {
      if(clazz.isInstance(obj)) {
        return obj;
      }
    }
    return null;
  }
  public Object getBean(String key) {
    return map.get(key);
  }
}
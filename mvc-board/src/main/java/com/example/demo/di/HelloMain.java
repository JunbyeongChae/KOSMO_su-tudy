package com.example.demo.di;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloMain {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("helloBean.xml");
    List<String> names = new ArrayList<>();
    HelloBean helloBean = new HelloBeanImpl();
    System.out.println(helloBean.getGreeting("Hi!!!!!!"));
    //System.out.println(helloBean);
    HelloBean helloBean2 = (HelloBean)context.getBean("helloBean");
    System.out.println(helloBean2.getGreeting("Hi!!!!!!"));
  }
}

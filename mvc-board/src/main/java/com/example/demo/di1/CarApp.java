package com.example.demo.di1;

import java.io.FileReader;
import java.util.Properties;

public class CarApp {
  static Object getObject(String key) throws Exception {
    //config.txt파일을 읽어서 Properties객체에 저장
    Properties prop = new Properties();
    prop.load(new FileReader("D:/workspace-board/mvc-board/src/main/java/com/example/demo/di1/config.txt"));
    //클래스 정보를 가져와 Class객체 저장
    Class clazz = Class.forName(prop.getProperty(key));
    return clazz.newInstance();//객체 생성 후 반환
  }
  public static void main(String[] args) throws Exception {
    Car myCar = (Car)getObject("car");
    System.out.println(myCar);
  }
}

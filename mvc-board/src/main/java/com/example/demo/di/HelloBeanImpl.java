package com.example.demo.di;

public class HelloBeanImpl implements HelloBean {
  private String msg = null;
  //setter객체 주입법 코드
  public void setMsg(String msg) {
    this.msg = msg;
  }
  @Override
  public String getGreeting(String msg) {
    return "Hello " + msg;
  }
  //Bean 초기화 후 호출되는 메서드
  public void initMethod() {
    System.out.println("Bean 초기화 메서드 호출");
  }
  //Bean 소멸 전 호출되는 메서드
  public void destroyMethod() {
    System.out.println("Bean 소멸 메서드 호출");
  }
}

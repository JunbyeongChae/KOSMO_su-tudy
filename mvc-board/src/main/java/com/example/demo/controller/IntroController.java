package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
@RequestMapping("/intro/*")
public class IntroController {
  @GetMapping("test1")
  public String test1() {
    log.info("test1() 호출");
    return "redirect:/intro/test1.jsp";//src/main/webapp/intro/test1.jsp
  }
  @GetMapping("test2")
  public String test2() {
    log.info("test2() 호출");
    return "forward:/intro/test2.jsp";//src/main/webapp/intro/test2.jsp
  }
  @GetMapping("test3")
  public String test3() {
    log.info("test3() 호출");
    return "intro/test3";//src/main/webapp/WEB-INF/views/intro/test3.jsp
  }
  
  
}

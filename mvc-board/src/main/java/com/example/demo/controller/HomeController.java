package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {
  @RequestMapping("/")
  public String home() {
    log.info("home() 호출");
    return "home.html";
  }
}

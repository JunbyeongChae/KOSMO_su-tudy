package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//스프링부트 기동시 실행
public class CorsController implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")//모든 요청에 대해
            .allowedOrigins("http://localhost:3000")//허용할 요청URL
            .allowedMethods("GET", "POST", "PUT", "DELETE")//허용할 메소드
            .allowedHeaders("*")//모든헤더 허용
            .allowCredentials(true)//인증정보 허용
            .maxAge(3600);//캐싱시간 3600초
  }
}

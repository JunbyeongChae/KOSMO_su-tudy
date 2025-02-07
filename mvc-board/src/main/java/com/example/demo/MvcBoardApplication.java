package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan //추가해야 우리가 만든 필터를 자동으로 스캔 해줌.
public class MvcBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcBoardApplication.class, args);
	}

}

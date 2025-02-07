package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
//현재 프로젝트 이름은 mvc_board
//프로젝트 이름을 매번 URL안에 작성할 것인가?
//-> mvc_board/슬래쉬 하면 그 앞에까지
@Log4j2
@Controller //jsp페이지로 내보낼 때 사용
@RequestMapping("/intro/*") //매 요청마다 반복되는 이름이므로 선언부에 공통으로 사용할 수 있도록 함.
public class IntroController {
    @GetMapping("test1")
    public String test1(HttpServletRequest request){
        log.info("test1호출 성공");
        String name = new String("바나나");
        request.setAttribute("name", name);
        //redirect이면 주소창이 바뀐다.
        return "redirect:/intro/test1.jsp";//src>main>webapp>intro>test1.jsp
    }//end of test1
    @GetMapping("test2")
    public String test2(HttpServletRequest request){
        log.info("test2호출 성공");
        String name = new String("사과");
        request.setAttribute("name", name);
        //forward이니까 주소창이 안바뀜. 출력내용은 바뀌어 있죠
        return "forward:/intro/test2.jsp";//src>main>webapp>intro>test2.jsp
    }//end of test2
    @GetMapping("test3")
    public String test3(){
        log.info("test3호출 성공");
        return "intro/test3";//src>main>webapp>WEB-INF>views>intro>test3.jsp
    }//end of test3    
}
/*
 * 요청 URL
 * 1) http://localhost:8000/intro/test1 엔터를 하면 IntroController클래스를 경유하고
 * return에 있는 정보로 응답페이지의 URL이 완성된다.
 * http://localhost:8000/intro/test1.jsp 요청하는 것은 IntroController클래스를 경유하지 않았다. - 문제제시
 * xxxx.jsp를 직접 요청한 것은 DispatcherServlet을 경유하지 않았다.
 * 표준서블릿을 사용하는가?  DispatcherServlet을 사용하는가? 차이점
 * http://localhost:8000/intro/test1.jsp 이렇게 요청하는 것은 스프링이 관여하지 않았다.
 * 첫번째는 URL이 변경되었으므로 기존에 요청은 끊어져 있다.
 * test1메소드 안에 생성된 객체가 있어도 test1.jsp에서는 사용할 수 없다.
 * String객체 생성은 IntroController.java엣 했는데 사용은 intro/test1.jsp에서 사용하고 싶다.
 * 어떡하지? 서로 다른 페이지 인데 하나의 정보를 공유하고 싶다면 어떻게 가능한가?
 * 자바로 인스턴스 하는것과 jsp로 인스턴스 하는 것은 차이가 있다.
 * 2) http://localhost:8000/intro/test2
 * 
 * 3) http://localhost:8000/intro/test3
 */
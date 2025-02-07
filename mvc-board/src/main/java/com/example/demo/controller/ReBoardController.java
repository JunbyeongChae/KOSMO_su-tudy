package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.logic.ReBoardLogic;
import com.example.demo.model.Member250120;
import com.example.demo.model.ReactBoard;
import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;

//선택하기
//1) @Controller인가, 2) @RestController 인가
//@Controller:화면을 지정할 수 있다. 와 @RestController-json형식으로 내보낸다. 차이점 - 구글 검색
//페이지로 출력결과를 내보낸다. - 1번 , 만일 나는 React페이지에 출력을 할거야 -2번
//반복되는 코드가 나오면 경계해야 한다. - 줄일 수 없나? - 문제의식 - 코드 변경 긍정적
//React와 Spring Boot 연동하기(3000 -> 8000)
//React서버 설정 -  src/setProxy.js -> 접두어 api
//Spring Boot설정
@Log4j2
@RestController
@RequestMapping("/api/*")
public class ReBoardController {
    @Autowired
    private ReBoardLogic reBoardLogic = null;//선언만 한다. 그러면 ApplicationContext관리해줌.
    //필요할 때 주입해준다.()
/**************************************************************
 * 게시글 목록 조회 구현하기     - search|select|where|GET
 * URL패핑 이름 : boardList
 **************************************************************/
    @GetMapping("board/boardList")
    public String boardList(@RequestParam Map<String, Object> pmap){
        log.info("boardList호출 성공");
        String temp = "[{'b_title':'글제목입니다.','b_content':'글내용입니다.'}]";
        List<Map<String,Object>> bList = null;
        bList = reBoardLogic.boardList(pmap);
        Gson g = new Gson();
        temp = g.toJson(bList);
        return temp;
    }//end of boardList
/**************************************************************
 * 게시글 등록 구현하기            - param(@RequestParam)|insert|POST
 *  URL패핑 이름 : boardInsert
 *  @return 1이면 등록 성공, 0이면 등록 실패
 **************************************************************/
    @PostMapping("board/boardInsert")
    public String boardInsert(@RequestBody ReactBoard board ){
        log.info("boardInsert호출 성공");
        log.info(board);
        int result = -1;//초기값을 -1로 한 이유는 0과 1이 의미있는 숫자임.
        return ""+result;//"-1"
    }
/**************************************************************
 * 게시글 수정 구현하기           - param|update|where|pk|PUT
 *  URL패핑 이름 : boardUpdate
 **************************************************************/
    @PutMapping("board/boardUpdate")
    public String boardUpdate(){
        log.info("boardUpdate호출 성공");
        int result = -1;//초기값을 -1로 한 이유는 0과 1이 의미있는 숫자임.
        return ""+result;//"-1"
    }
/**************************************************************
 * 게시글 삭제 구현하기          - pk|delete|where|DELETE
 *  URL패핑 이름 : boardDelete
 **************************************************************/
    @DeleteMapping("board/boardDelete")
    public String boardDelete(){
        log.info("boardDelete호출 성공");
        int result = -1;//초기값을 -1로 한 이유는 0과 1이 의미있는 숫자임.
        return ""+result;//"-1"
    }
}

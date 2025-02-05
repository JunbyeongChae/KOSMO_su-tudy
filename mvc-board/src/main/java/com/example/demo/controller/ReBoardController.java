package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/*")
public class ReBoardController {
  /************************************************************
    * 게시글 목록 조회 구현하기   - serch|select|where\GET
    * URL Mapping: /boardList
  ***********************************************************/
  @GetMapping("board/boardList")
  public String boardList() {
    log.info("boardList() 호출");
    String temp = "[{'b_title':'게시글 제목1','b_content':'게시글 내용1'}]";
    return temp;
  }
  /************************************************************
    * 게시글 등록 구현하기       - param(@RequestParam)|insert|POST
    * URL Mapping: /boardInsert
  ***********************************************************/
  /************************************************************
    * 게시글 수정 구현하기       - param|update|where(한 건 수정)|pk|PUT
    * URL Mapping: /boardUpdate
  ***********************************************************/
  /************************************************************
    * 게시글 삭제 구현하기       - pk|delete|where(한 건 삭제)|DELETE
    * URL Mapping: /boardDelete
  ***********************************************************/
}

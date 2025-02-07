package com.example.demo.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ReBoardDao;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReBoardLogic {
  @Autowired
  private ReBoardDao reBoardDao = null;
  public String boardList() {
    log.info("boardList() 호출");
    String temp = "[{'b_title':'게시글 제목1','b_content':'게시글 내용1'}]";
    return temp;
  }
}

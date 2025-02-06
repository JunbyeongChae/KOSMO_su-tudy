package com.example.demo.model;

import lombok.Data;

@Data
public class ReactBoard {
  private int b_no = 0;//게시글번호
  private String b_title = null;//게시글제목
  private String email = null;//작성자이메일
  private String b_content = null;//게시글내용
  private int b_hit = 0;//조회수
  private String b_date = null;//작성일
  private String b_file = null;//첨부파일
}
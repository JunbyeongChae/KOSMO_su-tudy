package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class ReBoardDao {
  private SqlSessionTemplate sessionTemplate = null;
  @Autowired
  private SqlSession sqlSession = null;

  public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
    log.info("boardList 호출 성공");
    List<Map<String, Object>> bList = null;
    bList = sessionTemplate.selectList("boardList", pMap);
    return bList;
  }
}

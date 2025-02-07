package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class ReBoardDao {
    @Autowired
    private SqlSessionTemplate sessionTemplate = null;
    public List<Map<String, Object>> boardList(Map<String, Object> pmap) {
        log.info("boardList 호출");
        //gubun은 검색조건의 컬럼명 - b_title, b_content, mem_nickname
        log.info(pmap.get("gubun")+", "+pmap.get("keyword"));
        List<Map<String,Object>> bList = null;
        bList = sessionTemplate.selectList("boardList", pmap);
        return bList;
    }
    
}

package com.example.demo.logic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ReBoardDao;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReBoardLogic {
    @Autowired
    private ReBoardDao reBoardDao = null;//절대로 new하지 않음.-빈관리를 받지않음.
    public List<Map<String, Object>> boardList(Map<String, Object> pmap) {
        log.info("boardList 호출 성공.");
        List<Map<String, Object>> bList = null;
        bList = reBoardDao.boardList(pmap);
        return bList;
    }
    
}

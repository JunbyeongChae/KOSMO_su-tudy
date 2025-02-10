package com.example.demo.di2;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DeptService {
  @Autowired
  private DeptDao deptDao = null;
      public List<Map<String, Object>> deptList() {
        log.info("deptList호출 성공");
        List<Map<String,Object>> list = null;
        list = deptDao.deptList();        
        return list;
    }
}

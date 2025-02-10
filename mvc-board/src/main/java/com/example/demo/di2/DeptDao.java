package com.example.demo.di2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeptDao {
  //주의 : Autowired에 required 속성은 기본값이 true이다.
  //필수 객체주입이 되야함. - 지금처럼 null일 경우 에러발생
  //required = false로 설정하면 객체주입이 안되어도 에러가 발생하지 않는다.
  @Autowired(required = false)
  private SqlSessionTemplate sqlSessionTemplate = null;

  public List<Map<String, Object>> deptList() {
    List<Map<String, Object>> list = new ArrayList<>();
    Map<String, Object> rmap = new HashMap<>();
    rmap.put("dname", "개발부");
    rmap.put("loc", "부산");
    list.add(rmap);
    return list;
  }
}

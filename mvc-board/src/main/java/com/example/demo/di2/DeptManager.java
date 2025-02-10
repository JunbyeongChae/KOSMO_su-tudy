package com.example.demo.di2;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.extern.log4j.Log4j2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

@Log4j2
public class DeptManager extends JFrame implements ActionListener {
  private ApplicationContext ctx = null;
  @Autowired
  private DeptController deptController = null;
  JPanel jp_north = new JPanel();
  JButton jbtn_sel = new JButton("조회");
  JButton jbtn_ins = new JButton("입력");
  JButton jbtn_upd = new JButton("수정");
  JButton jbtn_del = new JButton("삭제");

  public void initDisplay() {
    ctx = new AnnotationConfigApplicationContext(DeptCtx.class);
    deptController = ctx.getBean("deptController",DeptController.class);
    this.setTitle("부서관리");
    this.setSize(500, 400);
    jbtn_sel.addActionListener(this);
    jbtn_ins.addActionListener(this);
    jbtn_upd.addActionListener(this);
    jbtn_del.addActionListener(this);
    jp_north.setLayout(new FlowLayout(FlowLayout.LEFT));
    jp_north.add(jbtn_sel);
    jp_north.add(jbtn_ins);
    jp_north.add(jbtn_upd);
    jp_north.add(jbtn_del);
    this.add("North", jp_north);
    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (obj == jbtn_sel) {
      System.out.println("조회 버튼 클릭");
      List<Map<String, Object>> list = deptController.deptList();
      log.info(list);
    } else if (obj == jbtn_ins) {
      System.out.println("입력 버튼 클릭");
    } else if (obj == jbtn_upd) {
      System.out.println("수정 버튼 클릭");
    } else if (obj == jbtn_del) {
      System.out.println("삭제 버튼 클릭");
    }
  }

  public static void main(String[] args) {
    DeptManager dm = new DeptManager();
    dm.initDisplay();
  }
}

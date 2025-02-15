package com.example.demo.logic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ReBoardDao;
import com.example.demo.model.ReactBoard;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReBoardLogic {
  @Autowired
  private ReBoardDao reBoardDao;// 절대로 new하지 않음.-빈관리를 받지않음.

  public List<Map<String, Object>> boardList(Map<String, Object> pmap) {
    log.info("boardList 호출 성공.");
    List<Map<String, Object>> bList = null;
    bList = reBoardDao.boardList(pmap);
    return bList;
  }

  public String imageUpload(MultipartFile image) {
    String savePath = "D:\\workspace-board\\mvc-board\\src\\main\\webapp\\pds";
    String filename = null;
    String fullPath = null;
    if (image != null && !image.isEmpty()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      Calendar time = Calendar.getInstance();
      filename = sdf.format(time.getTime()) + "-" + image.getOriginalFilename().replaceAll(" ", "-");
      fullPath = savePath + "\\" + filename;
      try {
        File file = new File(fullPath);
        if (!file.getParentFile().exists()) {
          boolean dirsCreated = file.getParentFile().mkdirs();
          if (!dirsCreated) {
            log.error("디렉토리 생성 실패: " + file.getParentFile().getAbsolutePath());
            return null;
          }
        }
        byte[] bytes = image.getBytes();
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
          bos.write(bytes);
          bos.flush();
        }
        log.info("파일 저장 성공: " + fullPath);
        // 파일처리하는 경우 - 추가파일 정보가 필요할 때
        // 예 : 파일크기
        // double size = Math.floor(file.length() / (1024.0 * 1024.0) * 10) / 10;
        // log.info("size:" + size);
        // pmap.put("file_name", filename);
        // pmap.put("file_size", size);
        // pmap.put("file_path", fullPath);
      } catch (Exception e) {
        log.error("imageUpload Exception: " + e.toString());
        filename = null; // 예외 발생 시 null 반환
      }
    } else {
      log.warn("업로드된 파일이 없습니다.");
    }
    return filename;
  }

  public byte[] imageGet(String imageName) {
    log.info("imageGet 호출");
    String fname = null;
    byte[] fileArray = null;
    try {
      fname = URLDecoder.decode(imageName, "UTF-8");
      log.info("fname:" + fname);
    } catch (Exception e) {
      log.info("imageGet Exception:" + e.toString());
      e.printStackTrace();
    }
    return fileArray;
  }

  public int boardInsert(ReactBoard board) {
    int result = -1;
    result = reBoardDao.boardInsert(board);
    return result;
  }

  public List<Map<String, Object>> boardDetail(Map<String, Object> pmap) {
    List<Map<String, Object>> bList = null;
    bList = reBoardDao.boardList(pmap);
    if (bList.size() == 1) {
      reBoardDao.hitCount(pmap);
    }
    // 댓글가져오기
    List<Map<String, Object>> commList = reBoardDao.commentList(pmap);
    if (commList != null && commList.size() > 0) {
      Map<String, Object> cmap = new HashMap<>();
      cmap.put("comments", commList);
      bList.add(1, cmap);
    }
    return bList;
  }

  public int boardDelete(int b_no) {
    int result = -1;
    result = reBoardDao.boardDelete(b_no);
    log.info("Delete result:" + result);
    return result;
  }

  public int boardUpdate(Map<String, Object> pmap) {
    int result = -1;
    result = reBoardDao.boardUpdate(pmap);
    log.info("Update result:" + result);
    return result;
  }

  public int commentInsert(Map<String,Object> pmap) {
    int result = -1;
    result = reBoardDao.commentInsert(pmap);
    log.info("commentInsert result:" + result);
    return result;
  }
  
    public int commentUpdate(Map<String,Object> pmap) {
      int result = -1;
      result = reBoardDao.commentUpdate(pmap);
      log.info("commentUpdate result:" + result);
      return result;
    }

  public int commentDelete(int bc_no) {
    int result = -1;
    result = reBoardDao.commentDelete(bc_no);
    log.info("commentDelete result:" + result);
    return result;
  }
}

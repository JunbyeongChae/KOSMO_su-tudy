package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.logic.ReBoardLogic;
import com.example.demo.model.Member250120;
import com.example.demo.model.ReactBoard;
import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;

//선택하기
//1) @Controller인가, 2) @RestController 인가
//@Controller:화면을 지정할 수 있다. 와 @RestController-json형식으로 내보낸다. 차이점 - 구글 검색
//페이지로 출력결과를 내보낸다. - 1번 , 만일 나는 React페이지에 출력을 할거야 -2번
//반복되는 코드가 나오면 경계해야 한다. - 줄일 수 없나? - 문제의식 - 코드 변경 긍정적
//React와 Spring Boot 연동하기(3000 -> 8000)
//React서버 설정 -  src/setProxy.js -> 접두어 api
//Spring Boot설정
@Log4j2
@RestController
@RequestMapping("/api/*")
public class ReBoardController {
  @Autowired
  private ReBoardLogic reBoardLogic = null;// 선언만 한다. 그러면 ApplicationContext관리해줌.
  // 필요할 때 주입해준다.()

  //////////////// Quill Editor 사용하여 이미지 처리하기 구현 ///////////////
  // QuillEditor에서 이미지를 선택하면 <input type='file' name='image'
  // 누가 webapp/pds아래 파일을 만들어 주는거야
  @PostMapping("board/imageUpload")
  public String imageUpload(@RequestParam(value = "image") MultipartFile image) {
    log.info("image : " + image);
    String filename = reBoardLogic.imageUpload(image);
    return filename;
  }

  @GetMapping("board/imageGet")
  public String imageGet(HttpServletRequest req, HttpServletResponse res) {
    String b_file = req.getParameter("imageName");
    log.info("imageGet 호출 성공===>" + b_file);
    String filePath = "D:\\workspace-board\\mvc-board\\src\\main\\webapp\\pds"; // 절대경로.
    // String filePath ="upload"; // 절대경로.
    String fname = b_file;
    log.info("b_file: 8->euc" + b_file);
    File file = new File(filePath, b_file.trim());
    String mimeType = req.getServletContext().getMimeType(file.toString());
    // 브라우저는 모르는 mime type에 대해서는 다운로드 처리한다.
    // 보통 브라우저가 인지하는 ppt, xsl, word확장자 파일도 강제로 다운로드 처리 하고 싶을 때
    // application/octet-stream 를 마임타입으로 사용한다.
    if (mimeType == null) {
      // 강제로 이미지가 다운로드 되도록 처리 한다.
      res.setContentType("application/octet-stream");
    }
    String downName = null;
    FileInputStream fis = null;
    ServletOutputStream sos = null;
    try {
      if (req.getHeader("user-agent").indexOf("MSIE") == -1) {
        downName = new String(b_file.getBytes("UTF-8"), "8859_1");
      } else {
        downName = new String(b_file.getBytes("EUC-KR"), "8859_1");
      }
      res.setHeader("Content-Disposition", "attachment;filename=" + downName);
      fis = new FileInputStream(file);
      sos = res.getOutputStream();
      byte b[] = new byte[1024 * 10];
      int data = 0;
      while ((data = (fis.read(b, 0, b.length))) != -1) {
        sos.write(b, 0, data);
      }
      sos.flush();
    } catch (Exception e) {
      log.info(e.toString());
    } finally {
      try {
        if (sos != null)
          sos.close();
        if (fis != null)
          fis.close();
      } catch (Exception e2) {
        // TODO: handle exception
      }
    }
    return null;
  }// end of imageGet

  ////////////////////////////////////////////////////////////////////////

  /**************************************************************
   * 게시글 목록 조회 구현하기 - search|select|where|GET
   * URL패핑 이름 : boardList
   **************************************************************/
  @GetMapping("board/boardList")
  public String boardList(@RequestParam Map<String, Object> pmap) {
    log.info("boardList호출 성공");
    String temp = "[{'b_title':'글제목입니다.','b_content':'글내용입니다.'}]";
    List<Map<String, Object>> bList = null;
    bList = reBoardLogic.boardList(pmap);
    Gson g = new Gson();
    temp = g.toJson(bList);
    return temp;
  }// end of boardList

  /**************************************************************
   * 게시글 상세 조회 구현하기 - search|select|where|GET
   * URL패핑 이름 : boardDetail
   **************************************************************/
  @GetMapping("board/boardDetail")
  public String boardDetail(@RequestParam Map<String, Object> pmap) {
    log.info("boardDetail호출 성공");
    List<Map<String, Object>> bList = null;
    // 전체 조회와 다른 부분이 조회수 업데이트 처리하기 + 댓글이 있을 때 포함시키기
    bList = reBoardLogic.boardDetail(pmap);
    Gson g = new Gson();
    String temp = null;
    temp = g.toJson(bList);
    return temp;
  }// end of boardDetail

  /**************************************************************
   * 게시글 등록 구현하기 - param(@RequestParam)|insert|POST
   * URL패핑 이름 : boardInsert
   * 
   * @return 1이면 등록 성공, 0이면 등록 실패
   **************************************************************/
  @PostMapping("board/boardInsert")
  public String boardInsert(@RequestBody ReactBoard board) {
    log.info("boardInsert호출 성공");
    log.info(board);
    int result = -1;// 초기값을 -1로 한 이유는 0과 1이 의미있는 숫자임.
    result = reBoardLogic.boardInsert(board);
    return "" + result;// "-1"
  }

  /**************************************************************
   * 게시글 수정 구현하기 - param|update|where|pk|PUT
   * URL패핑 이름 : boardUpdate
   **************************************************************/
  @PutMapping("board/boardUpdate")
  public String boardUpdate(@RequestBody Map<String, Object> pmap) {
    log.info("boardUpdate호출 성공");
    int result = -1;// 초기값을 -1로 한 이유는 0과 1이 의미있는 숫자임.
    result = reBoardLogic.boardUpdate(pmap);
    log.info("result : " + result);
    return "" + result;
  }

  /**************************************************************
   * 게시글 삭제 구현하기 - pk|delete|where|DELETE
   * URL패핑 이름 : boardDelete
   **************************************************************/
  @DeleteMapping("board/boardDelete")
  public String boardDelete(@RequestParam(value = "b_no", required = true) int b_no) {
    log.info("boardDelete호출 성공");
    int result = -1;// 초기값을 -1로 한 이유는 0과 1이 의미있는 숫자임.
    result = reBoardLogic.boardDelete(b_no);
    return "" + result;// "-1"
  }

    /**************************************************************
   * 댓글 등록 구현하기 - insert|POST
   * URL패핑 이름 : commentInsert
   **************************************************************/
  @PostMapping("board/commentInsert")
  public String commentInsert(@RequestBody Map<String, Object> pmap) {
    log.info("commentInsert호출 성공");
    int result = -1;// 초기값을 -1로 한 이유는 0과 1이 의미있는 숫자임.
    result = reBoardLogic.commentInsert(pmap);
    return "" + result;// "-1"
  }
  /****************************************************************
   * 댓글 수정 구현하기 - update|PUT
   * URL패핑 이름 : commentUpdate
   ***************************************************************/
  @PutMapping("board/commentUpdate")
  public String commentUpdate(@RequestBody Map<String, Object> pmap) {
    log.info("commentUpdate호출 성공");
    int result = -1;// 초기값을 -1로 한 이유는 0과 1이 의미있는 숫자임.
    result = reBoardLogic.commentUpdate(pmap);
    return "" + result;// "-1"
  }
  /****************************************************************
   * 댓글 삭제 구현하기 - delete|DELETE
   * URL패핑 이름 : commentDelete
   ***************************************************************/
  @DeleteMapping("board/commentDelete")
  public String commentDelete(@RequestParam(value = "bc_no", required = true) int bc_no) {
    log.info("commentDelete호출 성공");
    int result = -1;// 초기값을 -1로 한 이유는 0과 1이 의미있는 숫자임.
    result = reBoardLogic.commentDelete(bc_no);
    return "" + result;// "-1"
  }
}

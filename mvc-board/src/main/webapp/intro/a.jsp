<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.demo.model.ReactBoard" %>
<%
    //스크립틀릿 - 자바코드
    //서버측에서 실행되는 코드 이다. html은 로컬에서 브라우저가 실행될때 출력
    //태그와 자바코드(서버결정됨)가 실행되는 시점이 서로 다르다.
    //자바코드가 가진 값과 태그가 섞임.
    ReactBoard rb = new ReactBoard();//인스턴스화이다.
%>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <!-- 
    jsp:useBean -> jsp액션태그라고 함. xml기반. 실제 태그이름은 useBean이고 jsp는 네임스페이스이다.
    <title>해리포터</title>
    <movie:title>해리포터</movie:title> movie는 namespace
    <book:title>해리포터</book:title> book은 namespace이다.
    useBean태그에 id값은 ReactBoard의 인스턴스 변수이다.
    -->
    <jsp:useBean id="rb2" class="com.example.demo.model.ReactBoard" scope="request"/>
    <h1>Heading</h1>
    <%
        //스크립틀릿
        rb2.setB_title("글 제목");//b_title = "" -> 글 제목
    %>
    <p>제목 : <%=rb2.getB_title()%></p>
</body>
</html>
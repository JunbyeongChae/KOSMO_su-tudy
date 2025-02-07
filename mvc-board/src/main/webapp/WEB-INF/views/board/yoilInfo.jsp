<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    //스크립틀릿 - 자바코드 작성가능 영역
    //ModelAndView는 scope 가 request이다.- 요청이 유지되는 동안에는 사용할 수 있다.
    //mav.addObject("year",2025);
    //request.getAttribute("year")이 부분이 null인 경우 toString() - 500번 가능성.
    int year = Integer.parseInt(request.getAttribute("year").toString());
    int month = Integer.parseInt(request.getAttribute("month").toString());
    int day = Integer.parseInt(request.getAttribute("day").toString());
    String yoil = request.getAttribute("yoil").toString();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>년도 <%=year%></h1>
    <h1>월 <%=month%></h1>
    <h1>일 <%=day%></h1>
    <h1>요일 <%=yoil%></h1>
</body>
</html>
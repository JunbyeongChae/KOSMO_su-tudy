<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>test2.jsp[webapp/intro]</title>
</head>
<body>
    <h1>test2.jsp페이지</h1>
<%
    String name = (String)request.getAttribute("name");
%>
    <p>이름 : <%=name%></p>
</body>
</html>
<!-- 
브라우저에서 test2.jsp를 직접 부르는 것은 IntroController를 거치지 않는 것이다.
http://localhost:8000/intro/test2 엔터 -> 주소창은 그대로 인데
출력화면은 test2.jsp내용이 보여진다.
요청이 계속 유지되고 있는 것으로 톰캣이 판단함.
이때만 test2메소드가 가진 정보를 test2.jsp에서 공유할 수 있다.
-->
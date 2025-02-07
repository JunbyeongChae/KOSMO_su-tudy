<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 
    jsp, servlet국한된게 아님
    //URL이 변한다.
    redirect === response.sendRedirect("XXX.jsp")
    //URL 이 변하지 않지만 화면은 변한다.
    //톰캣서버가 요청이 계속 유지되고 있다 라고 판단함. - 상태값 유지
    // 반드시 쿠키나 세션을 사용하지 않고도 값을 유지하는 방법이 있다. - request scope
    RequestDispatcher view = request.getRequestDispatcher("XXX.jsp");
    forward === view.forward(request, response)
-->
<!DOCTYPE html>
<html>
<head>
    <title>test1.jsp[webapp/intro]</title>
</head>
<body>
    <h1>test1.jsp입니다.</h1>
    <%
        String name = (String)request.getAttribute("name");
    %>
    <p>이름 : <%=name%></p>
</body>
</html>
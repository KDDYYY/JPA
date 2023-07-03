<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
    <title>홈</title>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8"/>
    <style>
        h3 {background-color: #aaffaa;
            text-align: center;}
    </style>
</head>
<body>
<h3>KIM SERVER</h3>
<c:if test="${empty sessionScope.me}">
    <button type="button" style = "float:right;" onclick="location.href='/login';">로그인</button>
    <button type="button" style = "float:right;" onclick="location.href='/register';">회원가입</button>
</c:if>

<c:if test="${not empty sessionScope.me}">
    <button type="button" style = "float:right;" onclick="location.href='/logout';">로그아웃</button>
    <button type="button" style = "float:right;" onclick="location.href='/withdraw';">회원탈퇴</button>
    <center><p>환영합니다 ${sessionScope.me.email} 님 </p></center>
</c:if>

</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
    <title>내 정보</title>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8"/>
    <style>
        h3 {background-color: #aaffaa;
            text-align: center;}
    </style>
</head>
<body>
<h3>내 정보</h3>

<div class="grid">
    <div>이름 : ${sessionScope.loginMember.name}</div>
    <div>이메일 : ${sessionScope.loginMember.email}</div>
    <div>비밀번호 : ${sessionScope.loginMember.pw}</div>
</div>
<div>  <button type="button" style = "float:right;" onclick="location.href='/withdraw';">회원탈퇴 </button></div>
<a href="/home">홈으로</a>
</body>
</html>
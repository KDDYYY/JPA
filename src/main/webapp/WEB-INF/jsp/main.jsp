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

<button type="button" style = "float:left;" onclick="location.href='/boards/new';">글쓰기</button>

<c:if test="${empty sessionScope.loginMember}">
    <button type="button" style = "float:right;" onclick="location.href='/memers/login';">로그인</button>
    <button type="button" style = "float:right;" onclick="location.href='/members/register';">회원가입</button>
</c:if>

<c:if test="${not empty sessionScope.loginMember}">
    <button type="button" style = "float:right;" onclick="location.href='/members/logout';">로그아웃</button>
    <button type="button" style = "float:right;" onclick="location.href='/myInfo';">내 정보</button>
    <center><p>환영합니다 ${sessionScope.loginMember.name} 님 </p></center>
</c:if>



<div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>제목</th>
            <th>내용</th>
            <th>수정날짜</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr th:each="board : ${boards}">
            <td th:text="${board.title}"></td>
            <td th:text="${board.content}"></td>
            <td th:text="${board.boardDate}"></td>
            </td>
        </tr>
        </tbody>
    </table>
</div>



</body>
</html>
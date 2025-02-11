<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MainPage</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            background-color: #f4f4f4;
        }

        .container {
            width: 50%;
            margin: 100px auto;
            background: white;
            padding: 20px;
            box-shadow: 0px 0px 10px gray;
            border-radius: 10px;
        }

        h2 {
            color: #333;
        }

        .btn-container {
            display: flex;
            justify-content: center;
            gap: 10px; /* 버튼 간격 조정 */
        }

        .btn-container a {
            display: inline-block;
            text-decoration: none;
        }

        button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .login-btn {
            background-color: #4CAF50;
            color: white;
        }

        .register-btn {
            background-color: #008CBA;
            color: white;
        }

        .list-btn {
            background-color: #f44336;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>간단한 회원가입 CRUD 연습</h2>
    <%-- 로그인 중 일때--%>
    <div class="btn-container">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <p>환영합니다, ${sessionScope.user.username}님!</p>
                <a href="/users/logout">
                    <button class="logout-btn">로그아웃</button>
                </a>
                <a href="/users/list">
                    <button class="list-btn">사용자 조회</button>
                </a>
            </c:when>
            <%-- 로그아웃 인 경우--%>
            <c:otherwise>
                <a href="/users/loginPage">
                    <button class="login-btn">로그인</button>
                </a>
                <a href="/users/registerPage">
                    <button class="register-btn">회원가입</button>
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>

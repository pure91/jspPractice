<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UserInfo</title>
    <script>
    </script>
</head>
<body>
<h2>사용자 조회</h2>
<a href="/users">
    <button>Main 돌아가기</button>
</a>
<a href="/users/list">
    <button>사용자 목록으로 돌아가기</button>
</a>

<table border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>사용자명</th>
        <th>이메일</th>
        <th>생성일자</th>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td>${userInfo.id}</td>
            <td>${userInfo.username}</td>
            <td>${userInfo.email}</td>
            <td>${userInfo.createdAt}</td>
        </tr>
    </tbody>
</table>
</body>
</html>
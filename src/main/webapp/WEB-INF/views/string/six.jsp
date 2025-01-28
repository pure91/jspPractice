<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>6번째 문제</title>
</head>
<body>
<h1>사용자 비밀번호 암호화 복호화 문제</h1>

<table border="1">
    <thead>
    <tr>
        <th>Message</th>
        <th>ID</th>
        <th>Original Password</th>
        <th>Encrypted Password</th>
        <th>Decrypted Password</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="userItem" items="${userInfoList}">
            <tr>
                <td>${userItem.message}</td>
                <td>${userItem.id}</td>
                <td>${userItem.password}</td>
                <td>${userItem.encryptedPassword}</td>
                <td>${userItem.decryptedPassword}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
<a href="/string">메인으로 돌아가기</a>
</html>
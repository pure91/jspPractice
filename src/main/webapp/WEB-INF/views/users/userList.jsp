<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UserList</title>
    <script>
    </script>
</head>
<body>
<h2>사용자 조회</h2>
<a href="/users">
    <button>Main 돌아가기</button>
</a>

<table border="1">
    <thead>
    <tr>
        <th>사용자명</th>
        <th>이메일</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.username}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
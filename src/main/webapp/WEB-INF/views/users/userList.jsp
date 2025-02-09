<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UserList</title>
    <script>
        function selectUser(row) {
            let userId = row.getAttribute("data-id");
            console.log("userId:", userId);
            location.href = "/users/detail?id=" + userId;
        }
    </script>
</head>
<style>
    /* 커서 손 모양 */
    tr.clickable {
        cursor: pointer;
    }
    /* 마우스 호버 배경색 */
    tr.clickable:hover {
        background-color: #f0f0f0;
    }

    /* 밑줄 추가 */
    tr.clickable td {
        padding: 10px;
    }

    tr.clickable:hover td {
        text-decoration: underline;
    }
</style>
<body>
<h2>사용자 목록 조회</h2>
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
        <tr data-id="${user.id}" onclick="selectUser(this)" class="clickable">
            <td>${user.username}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
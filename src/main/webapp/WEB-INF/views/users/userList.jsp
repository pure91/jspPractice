<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UserList</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            background-color: #f4f4f4;
        }

        .container {
            width: 70%;
            margin: 50px auto;
            background: white;
            padding: 20px;
            box-shadow: 0px 0px 10px gray;
            border-radius: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        tbody {
            text-align: center;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr.clickable:hover {
            background-color: #f0f0f0;
            cursor: pointer;
        }

        button {
            padding: 10px 15px;
            margin: 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: 0.3s;
        }
    </style>
    <script>
        function selectUser(row) {
            let userId = row.getAttribute("data-id");
            location.href = "/users/detail?id=" + userId;
        }
    </script>
</head>
<body>
<div class="container">
    <h2>사용자 목록 조회</h2>
    <a href="/users">
        <button>메인으로</button>
    </a>
    <table>
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
</div>
</body>
</html>
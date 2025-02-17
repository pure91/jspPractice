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
        // 사용자 선택
        function selectUser(row) {
            let userId = row.getAttribute("data-id");
            location.href = "/users/detail?id=" + userId;
        }

        // 검색 초기화
        function resetSearch(event) {
            event.preventDefault();
            let searchInput = document.querySelector('input[name="keyword"]');
            searchInput.value = '';
        }
    </script>
</head>
<body>
<div class="container">
    <h2>사용자 목록 조회</h2>

    <form action="/users/list" method="get">
        <input type="text" name="keyword" value="${keyword}" placeholder="사용자명 검색">
        <button type="submit">검색</button>
        <button onclick="resetSearch(event)">초기화</button>
    </form>

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
        <c:forEach var="user" items="${userList.list}">
            <tr data-id="${user.id}" onclick="selectUser(this)" class="clickable">
                <td>${user.username}</td>
                <td>${user.email}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- 페이징 처리 -->
    <div class="pagination">
        <%-- 1보다 클때 pageNum -1로 이전--%>
        <c:if test="${userList.pageNum > 1}">
            <a href="/users/list?pageNum=${userList.pageNum - 1}&pageSize=${userList.pageSize}&keyword=${keyword}">이전</a>
        </c:if>

        <%-- 페이징 번호 1부터 총 페이지수 까지 반복--%>
        <c:forEach begin="1" end="${userList.pages}" var="i">
            <c:choose>
                <%-- 현재 페이지면 그냥 숫자--%>
                <c:when test="${i == userList.pageNum}">
                    <span>${i}</span>
                </c:when>
                <%-- 현재 페이지와 다르면 링크 숫자 --%>
                <c:otherwise>
                    <a href="/users/list?pageNum=${i}&pageSize=${userList.pageSize}&keyword=${keyword}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <%-- 현재 페이지가 총 페이지수보다 작으면 +1로 다음 --%>
        <c:if test="${userList.pageNum < userList.pages}">
            <a href="/users/list?pageNum=${userList.pageNum + 1}&pageSize=${userList.pageSize}&keyword=${keyword}">다음</a>
        </c:if>
    </div>
</div>
</body>
</html>
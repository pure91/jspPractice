<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>3번째 문제</title>
</head>
<body>
<h1>3번째 문제 결과</h1>
<h2>도메인</h2>
<p>${domain}</p>
<h2>쿼리 파라미터</h2>
<table>
    <thead>
    <tr>
        <th>키</th>
        <th>값</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${queryParams}">
        <tr>
            <td>${item.key}</td>
            <td>${item.value}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>q 파라미터 키워드</h2>
<ul>
    <c:forEach var="keywordItem" items="${keywords}">
        <li>${keywordItem}</li>
    </c:forEach>
</ul>

<a href="/string">메인으로 돌아가기</a>
</body>
</html>
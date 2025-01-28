<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>4번째 문제</title>
</head>
<body>
<h1>price가 50원 이상인 제품List</h1>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>제품명</th>
        <th>가격</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="prdctItem" items="${productsMap}">
        <tr>
            <td>${prdctItem.key}</td>
            <td>${prdctItem.value.name}</td>
            <td style="text-align: left">
                <fmt:formatNumber value="${prdctItem.value.price}" type="number" />
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
<a href="/string">메인으로 돌아가기</a>
</html>
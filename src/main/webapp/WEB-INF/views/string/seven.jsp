<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>7번째 문제</title>
</head>
<body>
<h1>고객별 주문 목록</h1>

<table border="1">
    <thead>
    <tr>
        <th>고객별 주문 내역</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="customOrder" items="${customerOrderListMap}">
        <tr>
            <td>${customOrder.key} : ${customOrder.value}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h1>가격이 500 이상인 주문</h1>

<table border="1">
    <thead>
    <tr>
        <th>500원↑ 주문 제품</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="expensive" items="${expensiveOrderList}">
        <tr>
            <td>${expensive}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
<a href="/string">메인으로 돌아가기</a>
</html>
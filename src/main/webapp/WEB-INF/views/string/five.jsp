<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>5번째 문제</title>
</head>
<body>
<h1>고객정보</h1>
<p>ID : ${customerMap.id}</p>
<p>Name : ${customerMap.name}</p>
<p>Address : ${customerMap.address}</p>
<h1>제품 Id</h1>
<ul>
    <c:forEach var="productId" items="${productIdList}">
        <li>${productId}</li>
    </c:forEach>
</ul>
<h1>수량이 3개 이상인 제품 출력</h1>
<ul>
    <c:forEach var="entry" items="${filteredProductMap}">
        <li>Product ID : ${entry.value.productId}, Quantity: ${entry.value.quantity}</li>
    </c:forEach>
</ul>
</body>
<a href="/string">메인으로 돌아가기</a>
</html>
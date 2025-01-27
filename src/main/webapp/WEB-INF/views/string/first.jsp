<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>첫번째 문제</title>
</head>
<body>
<h1>첫번째 문제 결과</h1>
<%-- JSON 리스트 출력 --%>
<h2>사용자 정보 리스트 :</h2>
<ul>
    <c:forEach var="item" items="${jsonList}">
        <li>${item}</li>
    </c:forEach>
</ul>

<%-- 이메일 도메인 출력 --%>
<h2>도메인 리스트 :</h2>
<ul>
    <c:forEach var="domain" items="${domainSet}">
        <li>${domain}</li>
    </c:forEach>
</ul>

<c:if test="${empty jsonList}">
    <p>사용자 정보 리스트가 비어 있습니다.</p>
</c:if>

<c:if test="${empty domainSet}">
    <p>도메인 리스트가 비어 있습니다.</p>
</c:if>

<a href="/string">메인으로 돌아가기</a>
</body>
</html>
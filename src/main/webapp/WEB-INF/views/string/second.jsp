<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>2번째 문제</title>
</head>
<body>
<h1>2번째 문제 결과</h1>
<%-- JSON 리스트 출력 --%>
<h2>긴 문자열 split 추출 후 map 조회 :</h2>
<ul>
    <c:forEach var="log" items="${logsList}">
        <li>
            <c:forEach var="map" items="${log}">
                <strong>${map.key}:</strong> ${map.value}<br>
            </c:forEach>
        </li>
    </c:forEach>
</ul>

<a href="/string">메인으로 돌아가기</a>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
<%--    <link rel="stylesheet" href="/resources/static/css/showExcel.css"/>--%>
    <title>Welcome.</title>
</head>
<body>
<%--    <div id="menuBtn">--%>
<%--        <div id="btnLeft"><img src="/logo.gif" alt="logo"></div>--%>
<%--    </div>--%>
    <div id="content">
        <div class="subTitle"><p style="font-size:30px;">excel search</p></div>
        <%-- 이곳에 엑셀 조회 데이터 append해서 html 표시 --%>
        <div class="excelListDiv"></div>
    </div>
    <div class="printDiv"></div>

    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script type="text/javascript" src="/js/function.js"></script>
    <%-- 엑셀 데이터 조회 --%>
    <script>excelListTable();</script>
</body>
</html>
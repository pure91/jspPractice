<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome.</title>
</head>
<body>
    <div id="content">
        <div class="subTitle"><p style="font-size:30px;">DB 엑셀 데이터 조회</p></div>
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
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h2>회원가입 폼</h2>
    <form id="registerForm">
        <label for="username">아이디:</label>
        <input type="text" id="username" name="username"
               placeholder="아이디를 입력해주세요." required><br/>

        <label for="email">이메일:</label>
        <input type="text" id="email" name="email"
               placeholder="이메일을 입력해주세요" required>
        <button type="button" id="checkEmailBtn">중복확인</button>
        <span id="emailCheckResult"></span><br>

        <label for="password">비밀번호:</label>
        <input type="text" id="password" name="password"
               placeholder="비밀번호를 입력해주세요" required><br/>

        <button type="submit">회원가입</button>
    </form>

    <script>
        // 이메일 중복확인 체크
        $(document).ready(function (){
            $("#checkEmailBtn").click(function () {
                let email = $("#email").val();
                if (email === "") {
                    alert("이메일을 입력해주세요");
                    return;
                }

                $.ajax({
                    type: "POST",
                    url: "/users/signUp",
                    contentType: "application/json",
                    data: JSON.stringify({ "email": email }),
                    success: function (response) {
                        if (response.exists) {
                            $("#emailCheckResult").text("")
                        }
                    }
                })
            })
        })
    </script>
</body>
</html>
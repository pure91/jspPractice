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
<a href="/users">
    <button>Main 돌아가기</button>
</a>
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
    <input type="password" id="password" name="password"
           placeholder="비밀번호를 입력해주세요" required autocomplete="off"><br/>

    <button type="submit">회원가입</button>
</form>

<script>
    $(document).ready(function () {
        let isEmailChecked = false;

        // 이메일 중복확인
        $("#checkEmailBtn").click(function () {
            let email = $("#email").val();
            if (email === "") {
                alert("이메일을 입력해주세요");
                return;
            }

            $.ajax({
                type: "POST",
                url: "/api/users/checkEmail",
                data: {"email": email},
                success: function (result) {
                    if (result.success === true) {
                        $("#emailCheckResult").text(result.message).css("color", "green");
                        isEmailChecked = true;
                    } else {
                        $("#emailCheckResult").text(result.message).css("color", "red");
                        isEmailChecked = false;
                    }
                },
                error: function (xhr, status, error) {
                    console.log("xhr:", xhr);  // 전체 응답 객체
                    console.log("status:", status);  // 요청 상태 ("error", "timeout" 등)
                    console.log("error:", error);  // 서버에서 전달한 에러 메시지

                    alert("이메일 중복 확인 중 오류 발생");
                }
            });
        });

        // 회원가입 폼 제출
        $("#registerForm").submit(function (event) {
            event.preventDefault();

            if (!isEmailChecked) {
                alert("이메일 중복 확인을 해주세요.");
                return;
            }

            let formData = {
                username: $("#username").val(),
                email: $("#email").val(),
                password: $("#password").val()
            };

            $.ajax({
                type: "POST",
                url: "/api/users/signUp",
                contentType: "application/json",
                data: JSON.stringify(formData),
                // ajax에서 success(200~299)와 error(400~500)는 HTTP 상태코드에따라 실행됨
                success: function (result) {
                    if (result.success === true) {
                        alert(result.message);
                        location.replace("/users");
                    } else {
                        alert(result.message);
                    }
                },
                error: function (xhr, status, error) {
                    console.log("xhr:", xhr);  // 전체 응답 객체
                    console.log("status:", status);  // 요청 상태 ("error", "timeout" 등)
                    console.log("error:", error);  // 서버에서 전달한 에러 메시지

                    alert("회원가입 중 오류 발생");
                }
            });
        })
    });
</script>
</body>
</html>
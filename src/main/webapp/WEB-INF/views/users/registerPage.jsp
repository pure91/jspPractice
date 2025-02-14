<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            background-color: #f4f4f4;
        }
        .container {
            width: 40%;
            margin: 50px auto;
            background: white;
            padding: 20px;
            box-shadow: 0px 0px 10px gray;
            border-radius: 10px;
        }
        .form-group {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 15px;
        }
        label {
            width: 100px;
            font-weight: bold;
        }
        input {
            flex: 1;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        #checkEmailBtn {
            margin-left: 10px;
            padding: 8px;
            background-color: #008CBA;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            width: 20%;
        }
        button {
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
        }
        .small-btn {
            padding: 10px 15px;
            margin: 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: 0.3s;
            width: 30%;
            background-color: lightgray;
        }
        #emailCheckResult {
            margin-left: 10px;
            font-size: 12px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>회원가입</h2>
    <a href="/users">
        <button class="small-btn">메인으로</button>
    </a>
    <form id="registerForm" enctype="multipart/form-data">
        <div class="form-group">
            <label for="username">아이디:</label>
            <input type="text" id="username" name="username" placeholder="아이디를 입력해주세요." required>
        </div>

        <div class="form-group">
            <label for="email">이메일:</label>
            <input type="text" id="email" name="email" placeholder="이메일을 입력해주세요" required>
            <button type="button" id="checkEmailBtn">중복확인</button>
        </div>
        <span id="emailCheckResult"></span>

        <div class="form-group">
            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" placeholder="비밀번호를 입력해주세요" required autocomplete="off">
        </div>

        <div class="form-group">
            <label for="profileImage">프로필 이미지 :</label>
            <input type="file" id="profileImage" name="profileImage" accept="image/*">
        </div>

        <button type="submit">회원가입</button>
    </form>
</div>

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
                    console.log("xhr:", xhr);
                    console.log("status:", status);
                    console.log("error:", error);

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

            // 일반 데이터 추가
            let formData = new FormData();
            formData.append("username", $("#username").val());
            formData.append("email", $("#email").val());
            formData.append("password", $("#password").val());

            // 파일 추가
            let profileImage = $("#profileImage")[0].files[0];
            if (profileImage) {
                formData.append("profileImage", profileImage);
            }

            // let formData = {
            //     username: $("#username").val(),
            //     email: $("#email").val(),
            //     password: $("#password").val()
            // };

            $.ajax({
                type: "POST",
                url: "/api/users/signUp",
                // contentType: "application/json",
                // data: JSON.stringify(formData),
                contentType: false, // 자동으로 Content-type을 설정하지 않도록
                processData: false, // formData 사용으로 데이터 자동처리 방지
                data: formData,
                success: function (result) {
                    if (result.success === true) {
                        alert(result.message);
                        location.replace("/users");
                    } else {
                        alert(result.message);
                    }
                },
                error: function (xhr, status, error) {
                    console.log("xhr:", xhr);
                    console.log("status:", status);
                    console.log("error:", error);

                    alert("회원가입 중 오류 발생");
                }
            });
        })
    });
</script>
</body>
</html>
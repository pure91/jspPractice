<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>User Info</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            background-color: #f9f9f9;
        }
        .container {
            width: 50%;
            margin: 50px auto;
            background: white;
            padding: 20px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
        h2 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            table-layout: fixed;
            background: white;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
            word-wrap: break-word;
        }
        th {
            background-color: #008CBA;
            color: white;
        }
        td input {
            width: 90%;
            padding: 5px;
            text-align: center;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            padding: 10px 15px;
            margin: 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: 0.3s;
        }
        .edit-btn { background-color: #4CAF50; color: white; }
        .delete-btn { background-color: #f44336; color: white; }
        .save-btn { background-color: #ffa500; color: white; }
        .cancel-btn { background-color: #777; color: white; }
        button:hover {
            opacity: 0.8;
        }
    </style>
    <script>
        let isEmailChecked = false;

        function enableEdit() {
            document.getElementById("username").innerHTML = `<input type="text" id="editUsername" value="${userInfo.username}">`;
            document.getElementById("email").innerHTML = `
                <input type="text" id="editEmail" value="${userInfo.email}">
                <button type="button" class="save-btn" onclick="checkEmailExist()">중복확인</button>
            `;

            document.getElementById("editBtn").style.display = "none";
            document.getElementById("saveBtn").style.display = "inline-block";
            document.getElementById("cancelBtn").style.display = "inline-block";
        }

        function cancelEdit() {
            document.getElementById("username").innerHTML = "${userInfo.username}";
            document.getElementById("email").innerHTML = "${userInfo.email}";

            document.getElementById("editBtn").style.display = "inline-block";
            document.getElementById("saveBtn").style.display = "none";
            document.getElementById("cancelBtn").style.display = "none";
        }

        function checkEmailExist() {
            let currentEmail = "${userInfo.email}";
            let email = document.getElementById("editEmail").value;

            if (!email) {
                alert("이메일을 입력해주세요.");
                return;
            }

            $.ajax({
                type: "POST",
                url: "/api/users/checkEmail",
                data: { "currentEmail": currentEmail, "email": email },
                success: function (result) {
                    if (result.success) {
                        $("#emailCheckResult").text(result.message).css("color", "green");
                        isEmailChecked = true;
                    } else {
                        $("#emailCheckResult").text(result.message).css("color", "red");
                        isEmailChecked = false;
                    }
                },
                error: function () {
                    alert("이메일 중복 확인 중 오류 발생");
                }
            });
        }

        function saveEdit() {
            let newUsername = document.getElementById("editUsername").value;
            let newEmail = document.getElementById("editEmail").value;

            if (!newUsername) {
                alert("사용자명을 입력해주세요.");
                return;
            }
            if (!isEmailChecked) {
                alert("이메일 중복 확인을 해주세요.");
                return;
            }

            let formData = { id: ${userInfo.id}, username: newUsername, email: newEmail };

            $.ajax({
                type: "POST",
                url: "/api/users/updateUserInfo",
                contentType: "application/json",
                data: JSON.stringify(formData),
                success: function (result) {
                    alert(result.message);
                    if (result.success) location.reload();
                },
                error: function () {
                    alert("수정 중 오류 발생");
                }
            });
        }

        function deleteUserInfo() {
            if (!confirm("정말 삭제하시겠습니까?")) return;

            $.ajax({
                type: "POST",
                url: "/api/users/deleteUser/" + ${userInfo.id},
                success: function (result) {
                    alert(result.message);
                    if (result.success) location.replace("/users/list");
                },
                error: function () {
                    alert("삭제 중 오류 발생");
                }
            });
        }
    </script>
</head>
<body>
<div class="container">
    <h2>사용자 정보</h2>
    <a href="/users"><button>메인으로</button></a>
    <a href="/users/list"><button>사용자 목록</button></a>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>사용자명</th>
            <th>이메일</th>
            <th>생성일자</th>
            <th>수정일자</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${userInfo.id}</td>
            <td id="username">${userInfo.username}</td>
            <td id="email">${userInfo.email}</td>
            <td>${userInfo.createdAt}</td>
            <td>${userInfo.updatedAt}</td>
        </tr>
        </tbody>
    </table>

    <div id="emailCheckResult"></div>

    <div class="button-group">
        <button id="editBtn" class="edit-btn" onclick="enableEdit()">수정</button>
        <button id="saveBtn" class="save-btn" onclick="saveEdit()" style="display: none;">저장</button>
        <button id="cancelBtn" class="cancel-btn" onclick="cancelEdit()" style="display: none;">취소</button>
        <button id="deleteBtn" class="delete-btn" onclick="deleteUserInfo()">삭제</button>
    </div>
</div>
</body>
</html>
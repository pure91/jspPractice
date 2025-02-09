<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>UserInfo</title>
    <script>
        let isEmailChecked = false;

        // 수정 버튼
        function enableEdit() {
            // td를 input 태그로 변경
            document.getElementById("username").innerHTML = `<input type="text" id="editUsername" value="${userInfo.username}">`;
            document.getElementById("email").innerHTML = `<input type="text" id="editEmail" value="${userInfo.email}"> <button type="button" id="checkEmailBtn" onclick="checkEmailExist()">중복확인</button>`;

            // 버튼 변경
            document.getElementById("editBtn").style.display = "none";
            document.getElementById("saveBtn").style.display = "inline-block";
            document.getElementById("cancelBtn").style.display = "inline-block";
        }

        // 수정 취소
        function cancelEdit() {
            // 값 복원
            document.getElementById("username").innerHTML = "${userInfo.username}";
            document.getElementById("email").innerHTML = "${userInfo.email}";

            // 버튼 복원
            document.getElementById("editBtn").style.display = "inline-block";
            document.getElementById("saveBtn").style.display = "none";
            document.getElementById("cancelBtn").style.display = "none";
        }

        // 수정 중 이메일 중복 확인
        function checkEmailExist() {
            let currentEmail = "${userInfo.email}";
            console.log("@@@@@@@@@@@currentEmail:",currentEmail);
            let email = document.getElementById("editEmail").value;
            console.log("@@@@@@@@@@@email:",email);

            if (currentEmail === "" || email === "") {
                alert("이메일을 입력해주세요.");
                return;
            }

            $.ajax({
                type: "POST",
                url: "/api/users/checkEmail",
                data: {
                    "currentEmail": currentEmail,
                    "email": email
                },
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
        }

        // 수정 -> 저장
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

            let formData = {
                id: ${userInfo.id},
                username: newUsername,
                email: newEmail
            }

            $.ajax({
                type: "POST",
                url: "/api/users/updateUserInfo",
                contentType: "application/json",
                data: JSON.stringify(formData), // ajax에서는 data로 보내면 객체구나 무조건
                success: function (result) {
                    if (result.success === true) {
                        alert(result.message);
                        location.reload();
                    } else {
                        alert(result.message);
                    }
                },
                error: function (xhr, status, error) {
                    console.log("xhr:", xhr);  // 전체 응답 객체
                    console.log("status:", status);  // 요청 상태 ("error", "timeout" 등)
                    console.log("error:", error);  // 서버에서 전달한 에러 메시지

                    alert("수정 중 오류 발생");
                }
            })
        }

        // 삭제
        function deleteUserInfo () {
            let id = Number("${userInfo.id}");
            console.log("id:",id);
            console.log("type of id:",typeof id);
            if (!confirm("정말 삭제하시겠습니까?")){ // 확인 시 true로 if문 빠짐, 취소 시 false로 return;
                return;
            }

            $.ajax({
                type : "POST",
                url : "/api/users/deleteUser/" + id,
                success: function (result) {
                    if (result.success === true) {
                        alert(result.message);
                        location.replace("/users/list");
                    } else {
                        alert(result.message);
                    }
                },
                error : function (xhr, status, error) {
                    console.log("xhr:", xhr);  // 전체 응답 객체
                    console.log("status:", status);  // 요청 상태 ("error", "timeout" 등)
                    console.log("error:", error);  // 서버에서 전달한 에러 메시지

                    alert("삭제 중 오류 발생");
                }
            })
        }
    </script>
</head>
<body>
<h2>사용자 조회</h2>
<a href="/users">
    <button>Main 돌아가기</button>
</a>
<a href="/users/list">
    <button>사용자 목록으로 돌아가기</button>
</a>

<table border="1">
    <thead>
    <tr>
        <th>id</th>
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
<button id="editBtn" onclick="enableEdit()">수정</button>
<div id="emailCheckResult"></div>
<button id="saveBtn" onclick="saveEdit()" style="display: none;">저장</button>
<button id="cancelBtn" onclick="cancelEdit()" style="display: none;">취소</button>
<button id="deleteBtn" onclick="deleteUserInfo()">삭제</button>
</body>
</html>
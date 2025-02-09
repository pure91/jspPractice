package org.example.hmpractice.users.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UsersDTO {
    private int id;                 // 식별키
    private String username;        // 닉네임 = 아이디
    private String email;           // 이메일
    private String password;        // 비밀번호
    private Timestamp createdAt;    // 생성일자
    private Timestamp updatedAt;    // 수정일자
    private Character deleteYn;     // 삭제여부
    private Timestamp deletedAt;    // 삭제일자
}

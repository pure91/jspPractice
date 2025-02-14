package org.example.hmpractice.files.dto;

import lombok.Data;

@Data
public class UsersProfileDTO {
    private int userId;                 // 사용자 식별 아이디
    private String profileImagePath;    // 프로필 이미지 경로
}

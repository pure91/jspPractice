package org.example.hmpractice.files.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UsersProfileDTO {
    private int fileSn;                 // 파일 Serial Number
    private int userId;                 // 사용자 식별 아이디
    private String filePath;            // 파일 경로
    private String fileName;            // 파일명
    private long fileSize;              // 파일 크기
    private String fileType;            // 파일 유형
    private Timestamp createdAt;        // 프로필 사진 생성일자
    private Timestamp updatedAt;        // 프로필 사진 수정일자
    private Character deleteYn;         // 프로필 사진 삭제여부
    private Timestamp deletedAt;        // 프로필 사진 삭제일자
}

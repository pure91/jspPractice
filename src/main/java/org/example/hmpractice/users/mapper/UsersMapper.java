package org.example.hmpractice.users.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.hmpractice.files.dto.UsersProfileDTO;
import org.example.hmpractice.users.dto.UsersDTO;

import java.util.List;

@Mapper
public interface UsersMapper {

    // 이메일 중복 확인
    int countByEmail(String email);

    // 회원가입
    int registerUserInfo(UsersDTO usersDTO);

    // 파일 업로드
    int insertFile(UsersProfileDTO usersProfileDTO);

    // 사용자 목록 조회(페이징)
    List<UsersDTO> getUserList(String keyword);

    // 사용자 정보 조회
    UsersDTO getUserDetail(int id);

    // 사용자 정보 수정
    int updateUserInfo(UsersDTO usersDTO);

    // 사용자 삭제
    int deleteUser(int id);

    // User 로그인 정보 조회
    UsersDTO getUserEmail(String email);

}

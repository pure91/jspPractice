package org.example.hmpractice.users.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.hmpractice.users.dto.UsersDTO;

import java.util.List;

@Mapper
public interface UsersMapper {

    // 이메일 중복 확인
    int countByEmail(String email);

    // 회원가입
    int registerUserInfo(UsersDTO usersDTO);

    // 사용자 조회
    List<UsersDTO> getUserList();
}

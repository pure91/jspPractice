package org.example.hmpractice.users.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.hmpractice.users.dto.UsersDTO;

@Mapper
public interface UsersMapper {

    // 회원가입 -> 이메일 중복 확인
    int countByEmail(String email);

    // 회원가입 -> 등록
    UsersDTO registerUserInfo(UsersDTO usersDTO);
}

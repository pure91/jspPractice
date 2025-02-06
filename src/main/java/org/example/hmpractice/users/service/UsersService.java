package org.example.hmpractice.users.service;

import org.example.hmpractice.users.dto.UsersDTO;
import org.example.hmpractice.users.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    UsersMapper usersMapper;

    // 회원가입 -> 이메일 중복 확인
    public boolean isEmailExists(String email) {
        int count = usersMapper.countByEmail(email);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 회원가입 -> 등록
    public UsersDTO registerUser(UsersDTO usersDTO) {
        return usersMapper.registerUserInfo(usersDTO);
    }
}

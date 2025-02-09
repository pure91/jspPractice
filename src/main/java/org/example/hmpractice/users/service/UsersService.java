package org.example.hmpractice.users.service;

import org.example.hmpractice.users.dto.UsersDTO;
import org.example.hmpractice.users.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    UsersMapper usersMapper;

    // 이메일 중복 확인
    public boolean isEmailExists(String email) {
        int count = usersMapper.countByEmail(email);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 회원가입
    public void registerUser(UsersDTO usersDTO) {
        int result = usersMapper.registerUserInfo(usersDTO);

        if (result == 0) {
            throw new RuntimeException("회원가입에 실패하였습니다");
        }
    }

    // 사용자 목록 조회
    public List<UsersDTO> getAllUsers() {
        return usersMapper.getUserList();
    }

    // 사용자 정보 조회
    public UsersDTO getUserInfo(int id) {
        return usersMapper.getUserDetail(id);
    }
}

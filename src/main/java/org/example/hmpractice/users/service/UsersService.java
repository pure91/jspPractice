package org.example.hmpractice.users.service;

import org.example.hmpractice.users.dto.UsersDTO;
import org.example.hmpractice.users.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

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
        String encodedPassword = passwordEncoder.encode(usersDTO.getPassword());
        usersDTO.setPassword(encodedPassword);
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

    // 사용자 정보 수정
    public void updateUserInfo(UsersDTO usersDTO) {
        int result = usersMapper.updateUserInfo(usersDTO);

        if (result == 0) {
            throw new RuntimeException("정보수정에 실패하였습니다");
        }
    }

    // 사용자 삭제
    public void deleteUser(int id) {
        int result = usersMapper.deleteUser(id);

        if (result == 0) {
            throw new RuntimeException("삭제에 실패하였습니다");
        }
    }
}

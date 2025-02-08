package org.example.hmpractice.users.controller;

import lombok.extern.log4j.Log4j2;
import org.example.hmpractice.users.dto.UsersDTO;
import org.example.hmpractice.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/users")
public class UsersRestController {

    @Autowired
    UsersService usersService;

    // 이메일 중복 체크 API
    @PostMapping("/checkEmail")
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        String email = request.get("email");

        boolean existUserEmail = usersService.isEmailExists(email);
        log.debug("existUserEmail: {}", existUserEmail);

        if (existUserEmail) {
            result.put("success", false);
            result.put("message", "중복된 이메일입니다.");
        } else {
            result.put("success", true);
            result.put("message", "사용 가능한 이메일입니다.");
        }

        return ResponseEntity.ok(result); // 단순 중복체크니까 길게 쓸 필요없이 200 반환
    }

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody UsersDTO usersDTO) {
        Map<String, Object> result = new HashMap<>();

        // 이메일 중복 확인
        boolean existUserEmail = usersService.isEmailExists(usersDTO.getEmail());
        log.debug("signUp -> existUserEmail: {}", existUserEmail);

        if (existUserEmail) {
            result.put("success", false);
            result.put("message", "이메일 중복 확인을 해주세요.");
            return ResponseEntity.ok(result);
        }

        try {
            // 회원 가입
            usersService.registerUser(usersDTO);
            result.put("success", true);
            result.put("message", "회원가입에 성공하였습니다.");
            result.put("user", usersDTO);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "회원가입에 실패하였습니다.");
            log.debug("회원가입 실패:{}", e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result); // insert니까 정확하게 201 상태를 보내줌
    }
}

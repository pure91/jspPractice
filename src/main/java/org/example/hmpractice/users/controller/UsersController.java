package org.example.hmpractice.users.controller;

import lombok.extern.log4j.Log4j2;
import org.example.hmpractice.users.dto.UsersDTO;
import org.example.hmpractice.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    // 메인 페이지
    @GetMapping("")
    public String mainPage() {
        return "users/mainPage";
    }

    // 회원가입 입력 페이지
    @GetMapping("/register")
    public String registerPage() {
        return "users/registerPage";
    }

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

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody UsersDTO usersDTO) {
        Map<String, Object> result = new HashMap<>();

        // 이메일 중복확인 체크 확인
        boolean existUserEmail = usersService.isEmailExists(usersDTO.getEmail());
        log.debug("signUp -> existUserEmail: {}", existUserEmail);

        if (existUserEmail) {
            result.put("success", false);
            result.put("message", "이메일 중복 확인을 해주세요.");
            return ResponseEntity.status(HttpStatus.OK).body(result);
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

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

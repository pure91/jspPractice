package org.example.hmpractice.users.controller;

import lombok.extern.log4j.Log4j2;
import org.example.hmpractice.files.dto.UsersProfileDTO;
import org.example.hmpractice.users.dto.UsersDTO;
import org.example.hmpractice.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<Map<String, Object>> checkEmail(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "currentEmail", required = false) String currentEmail) {

        Map<String, Object> result = new HashMap<>();
        log.debug("email{}, currentEmail{}: ", email, currentEmail);

        // 만약 수정하려는 이메일이 현재 이메일과 같을 경우 허용
        if (email.equals(currentEmail)) {
            result.put("success", true);
            result.put("message", "사용 가능한 이메일입니다.");
            return ResponseEntity.ok(result);
        }

        boolean existUserEmail = usersService.isEmailExists(email);
        log.debug("existUserEmail: {}", existUserEmail);

        if (existUserEmail) {
            result.put("success", false);
            result.put("message", "중복된 이메일입니다.");
        } else {
            result.put("success", true);
            result.put("message", "사용 가능한 이메일입니다.");
        }

        return ResponseEntity.ok(result);
    }

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestPart(value = "usersDTO") UsersDTO usersDTO,
                                                      @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

        // 이메일 중복 확인
        boolean existUserEmail = usersService.isEmailExists(usersDTO.getEmail());
        log.debug("signUp -> existUserEmail: {}", existUserEmail);

        Map<String, Object> result = new HashMap<>();

        if (existUserEmail) {
            result.put("success", false);
            result.put("message", "이메일 중복 확인을 해주세요.");
            return ResponseEntity.ok(result);
        }

        try {
            // 회원 가입
            usersService.registerUser(usersDTO);

            // 프로필 사진 업로드
            if (profileImage != null && !profileImage.isEmpty()) {
                usersService.saveUserProfile(usersDTO.getId(), profileImage);
            }

            result.put("success", true);
            result.put("message", "회원가입에 성공하였습니다.");
            result.put("user", usersDTO);
        } catch (IOException e) {
            result.put("success", false);
            result.put("message", "파일 업로드 중 오류가 발생했습니다. " + e.getMessage());
            log.info("파일 업로드 오류:{}", e.getMessage());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "회원가입에 실패하였습니다." + e.getMessage());
            log.info("회원가입 실패:{}", e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    // 사용자 정보 수정
    @PostMapping("/updateUserInfo")
    public ResponseEntity<Map<String, Object>> updateUserInfo(@RequestBody UsersDTO usersDTO) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 정보 수정
            usersService.updateUserInfo(usersDTO);
            result.put("success", true);
            result.put("message", "정보수정 완료");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "정보수정 실패");
            log.debug("정보수정 실패:{}", e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    // 사용자 삭제
    @PostMapping("/deleteUser/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable(value = "id") int id) {
        Map<String, Object> result = new HashMap<>();

        try {
            usersService.deleteUser(id);
            result.put("success", true);
            result.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "삭제 실패");
            log.debug("삭제 실패:{}", e.getMessage());
        }

        return ResponseEntity.ok(result);
    }
}

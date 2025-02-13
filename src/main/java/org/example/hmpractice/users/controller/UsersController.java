package org.example.hmpractice.users.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.example.hmpractice.users.dto.UsersDTO;
import org.example.hmpractice.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 메인 페이지 이동
    @GetMapping("")
    public String mainPage() {
        return "users/mainPage";
    }

    // 회원가입 입력 페이지 이동
    @GetMapping("/registerPage")
    public String registerPage() {
        return "users/registerPage";
    }

    // 로그인 페이지 이동
    @GetMapping("/loginPage")
    public String loginPage() {
        return "users/loginPage";
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        UsersDTO user = usersService.findByEmail(email);

        // 비밀번호 확인
        System.out.println("입력한 비밀번호: " + password);
        System.out.println("DB 비밀번호: " + user.getPassword());
        System.out.println("비밀번호 일치 여부: " + passwordEncoder.matches(password, user.getPassword()));

        // 암호화 쓴 비밀번호만 matches가 먹히는거였네
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
            return "users/loginPage";
        }

        session.setAttribute("user", user);
        return "redirect:/users";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users";
    }

    // 사용자 목록 조회
    @GetMapping("/list")
    public String getUserList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "") String keyword,
            Model model) {
        PageInfo<UsersDTO> userList = usersService.getAllUsers(pageNum, pageSize, keyword);
        model.addAttribute("userList", userList);
        model.addAttribute("keyword", keyword);
        return "users/userList";
    }

    // 사용자 정보 조회
    @GetMapping("/detail")
    public String getUserDetail(Model model, @RequestParam(value = "id", required = false) int id) {
        UsersDTO userInfo = usersService.getUserInfo(id);
        model.addAttribute("userInfo", userInfo);
        log.debug("userInfo:{}", userInfo);
        return "users/userDetail";
    }
}

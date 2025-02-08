package org.example.hmpractice.users.controller;

import lombok.extern.log4j.Log4j2;
import org.example.hmpractice.users.dto.UsersDTO;
import org.example.hmpractice.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

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

    // 사용자 조회
    @GetMapping("/list")
    public String getUserList(Model model) {
        List<UsersDTO> userList = usersService.getAllUsers();
        model.addAttribute("userList", userList);
        return "users/userList";
    }
}

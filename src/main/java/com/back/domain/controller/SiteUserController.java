package com.back.domain.controller;

import com.back.domain.dto.UserCreateForm;
import com.back.domain.dto.UserLoginForm;
import com.back.domain.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class SiteUserController {
    private final SiteUserService siteUserService;

    @GetMapping("/login")
    public String login(UserLoginForm userLoginForm) {
        return "user/login_form";
    }

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        // 아이디 중복 확인
        if (siteUserService.findByUsername(userCreateForm.getUsername()) != null) {
            bindingResult.rejectValue("username", "duplicate", "이미 사용 중인 아이디입니다.");
            return "user/signup_form";
        }

        // 비밀번호 체크 확인
        if (!userCreateForm.getPassword().equals(userCreateForm.getPasswordCheck())) {
            bindingResult.rejectValue("passwordCheck", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user/signup_form";
        }

        if (bindingResult.hasErrors()) {
            return "user/signup_form";
        }

        siteUserService.write(userCreateForm.getUsername(), userCreateForm.getPassword(), userCreateForm.getPasswordCheck(), userCreateForm.getEmail());

        return "redirect:/user/login";
    }
}
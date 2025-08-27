package com.back.domain.controller;

import com.back.domain.dto.UserCreateForm;
import com.back.domain.dto.UserLoginForm;
import com.back.domain.service.SiteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class SiteUserController {
    @Autowired
    private SiteUserService siteUserService;

    @GetMapping("/login")
    public String login(UserLoginForm userLoginForm) {
        return "user/login_form";
    }

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup_form";
    }
}
package com.back.domain.controller;

import com.back.domain.dto.UserCreateForm;
import com.back.domain.dto.UserLoginForm;
import com.back.domain.service.SiteUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/signup_form";
        }

        try {
            siteUserService.write(userCreateForm.getUsername(), userCreateForm.getPassword());
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("username", "duplicate", e.getMessage());
            return "user/signup_form";
        }

        return "redirect:/user/login";
    }
}
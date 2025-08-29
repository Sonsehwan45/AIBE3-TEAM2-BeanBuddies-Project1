package com.back.jsb.controller;

import com.back.jsb.dto.UserCreateForm;
import com.back.jsb.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/siteusers")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signUp(UserCreateForm userCreateForm) {
        return "siteuser/signup_form";
    }

    @PostMapping("/signup")
    public String doSignUp(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("userCreateForm", userCreateForm);

            return "siteuser/signup_form";
        }

        if (!userCreateForm.password().equals(userCreateForm.confirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "passwordIncorrect",
                    "입력하신 두 비밀번호가 서로 다릅니다.");

            model.addAttribute("userCreateForm", userCreateForm);
            return "/siteuser/signup_form";
        }

        try {
            userService.join(userCreateForm);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "siteuser/login_form";
    }
}
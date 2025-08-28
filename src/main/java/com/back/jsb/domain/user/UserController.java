package com.back.jsb.domain.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/sign")
    public String showSignUp(@ModelAttribute("form") UserCreateForm form) {
        return "/user/signup_form";
    }

    @PostMapping("/sign")
    public String doSignUp(
            @Valid @ModelAttribute("form") UserCreateForm form,
            BindingResult bindingResult)
    {
        // 공백 확인(@NotBlank로)
        if (bindingResult.hasErrors()) {
            return "/user/signup_form";
        }

        //아이디, 닉네임 중복 확인
        if (userService.existsByUsername(form.getUsername())) {
            bindingResult.rejectValue("username", "duplicate", "이미 사용중인 아이디입니다.");
            return "/user/signup_form";
        }
        if (userService.existsByNickname(form.getNickname())) {
            bindingResult.rejectValue("nickname", "duplicate", "이미 사용중인 닉네임입니다.");
            return "/user/signup_form";
        }

        // 비밀번호 확인
        if(!form.getPassword().equals(form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "mismatch", "비밀번호가 일치하지 않습니다.");
            return "/user/signup_form";
        }

        userService.register(form);

        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String showLogin(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }
        return "/user/login_form";
    }


}
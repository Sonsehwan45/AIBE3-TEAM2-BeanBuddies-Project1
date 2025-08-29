package com.back.domain.controller;

import com.back.domain.dto.UserCreateForm;
import com.back.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor // final 필드를 포함하는 생성자를 자동으로 만들어줍니다.
@Controller // 이 클래스가 스프링의 컨트롤러임을 나타냅니다.
@RequestMapping("/user") // 이 컨트롤러의 모든 메서드는 "/user"로 시작하는 URL에 매핑됩니다.
public class UserController {

    private final UserService userService; // 회원가입 로직 처리를 위한 서비스

    @GetMapping("/signup") // GET 방식의 /user/signup 요청을 처리합니다.
    public String signup(UserCreateForm userCreateForm) {
        // signup_form.html 템플릿을 렌더링하여 보여줍니다.
        return "signup_form";
    }

    @PostMapping("/signup") // POST 방식의 /user/signup 요청을 처리합니다.
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        // @Valid: UserCreateForm의 유효성 검사를 수행합니다.
        // BindingResult: 유효성 검사 결과를 담는 객체입니다.
        if (bindingResult.hasErrors()) {
            // 만약 DTO 유효성 검사에 실패하면, 다시 회원가입 폼을 보여줍니다.
            return "signup_form";
        }

        // 비밀번호와 비밀번호 확인 필드가 일치하는지 확인합니다.
        if (!userCreateForm.getPassword().equals(userCreateForm.getPasswordConfirm())) {
            // 일치하지 않으면 에러 메시지를 추가하고 다시 회원가입 폼을 보여줍니다.
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            // userService를 통해 새로운 사용자를 생성합니다.
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword());
        } catch (DataIntegrityViolationException e) {
            // 만약 사용자 ID 또는 이메일이 이미 존재하여 DB 제약조건을 위반하면 예외가 발생합니다.
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }

        // 모든 과정이 성공하면 질문 목록 페이지로 리다이렉트(재이동)합니다.
        return "redirect:/question/list";
    }

    @GetMapping("/login") // GET 방식의 /user/login 요청을 처리합니다.
    public String login() {
        // login_form.html 템플릿을 렌더링하여 보여줍니다.
        return "login_form";
    }
}
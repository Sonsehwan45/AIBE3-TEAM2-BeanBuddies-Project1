package com.back.jsb.domain.user;

import com.back.jsb.global.security.UserSecurity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("/sign")
    public String signUp(@ModelAttribute("form") UserCreateForm form) {
        return "/signup_form";
    }

    @PostMapping("/sign")
    public String signUp(
            @Valid @ModelAttribute("form") UserCreateForm form,
            BindingResult bindingResult)
    {
        // 공백 확인
        if (bindingResult.hasErrors()) {
            return "/signup_form";
        }

        //아이디, 닉네임 중복 확인
        if (userService.existsByUsername(form.getUsername())) {
            bindingResult.rejectValue("username", "duplicate", "이미 사용중인 아이디입니다.");
            return "/signup_form";
        }
        if (userService.existsByNickname(form.getNickname())) {
            bindingResult.rejectValue("nickname", "duplicate", "이미 사용중인 닉네임입니다.");
            return "/signup_form";
        }

        // 비밀번호 확인
        if(!form.getPassword().equals(form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "mismatch", "비밀번호가 일치하지 않습니다.");
            return "/signup_form";
        }

        userService.register(form);

        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String showLogin(
            @RequestParam(value = "error", required = false) String error,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("loginError", "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }
        return "/login_form";
    }


    @GetMapping("/profile")
    public String profile(
            Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserSecurity userSecurity
    ) {
        User user = userSecurity.getUser();
        ProfileForm form = new ProfileForm(user);

        //DB 프로필 이미지 처리
        String originalBase64 = null;
        try {
            originalBase64 = imageService.getBase64UserImage(user);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("msg", "이미지 처리 중 오류가 발생했습니다.");
            return "redirect:/user/profile";
        }

        model.addAttribute("profileForm", form);
        model.addAttribute("profileImageCurrent", null);
        model.addAttribute("profileImageOriginal", originalBase64);
        model.addAttribute("originalNickname", user.getNickname());

        return "profile";
    }

    @PostMapping("/profile")
    public String modify(
            Model model,
            @Valid @ModelAttribute ProfileForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserSecurity userSecurity
    ) {
        User user = userSecurity.getUser();

        //공백 확인
        if (bindingResult.hasErrors()) {

            String originalBase64 = null;
            String currentBase64 = null;
            try {
                originalBase64 = imageService.getBase64UserImage(user);
                currentBase64 = imageService.getBase64EditingImage(user, form.profileImage());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("msg", "이미지 처리 중 오류가 발생했습니다.");
                return "redirect:/user/profile";
            }

            model.addAttribute("profileImageOriginal", originalBase64);
            model.addAttribute("profileImageCurrent", currentBase64);
            model.addAttribute("originalNickname", user.getNickname());
            return "/profile";
        }

        userService.modify(user, form);
        redirectAttributes.addFlashAttribute("msg", "회원 정보가 변경되었습니다.");
        return "redirect:/user/profile";
    }

    @GetMapping("/password") // 비밀번호 변경
    public String password(@ModelAttribute("form") PasswordForm form) {
        return "/password_form";
    }

    @PostMapping("/password")
    public String password(@Valid @ModelAttribute("form") PasswordForm form, BindingResult bindingResult, @AuthenticationPrincipal UserSecurity userSecurity) {
        // 현재 로그인 중인 유저 데이터 받아오기
        User user = userSecurity.getUser();

        // 기존 비밀번호 정상 입력 확인
        if (!userService.matchPassword(form.getOldPassword(), user.getPassword())) {
            bindingResult.rejectValue("oldPassword", "invalid.oldPassword", "기존 비밀번호가 올바르지 않습니다.");
        }
        
        // 신규 비밀번호가 기존 비밀번호와 중복인지 체크
        if (userService.matchPassword(form.getPassword(), user.getPassword())) {
            bindingResult.rejectValue("password", "duplicatePassword", "기존 비밀번호와 중복됩니다.");
        }

        // 비밀번호 확인 체크
        if(!form.getPassword().equals(form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "mismatch", "비밀번호가 일치하지 않습니다.");
        }

        // 기타 오류 확인 후 반환
        if (bindingResult.hasErrors()) {
            return "/password_form";
        }

        userService.changePassword(user, form.getPassword());

        return "redirect:/";
    }

    @GetMapping("/password/reset")
    public String reset(@ModelAttribute("form") PasswordResetForm form) {
        return "/password_reset_form";
    }
    
    @PostMapping("/password/reset")
    public String reset(@Valid @ModelAttribute("form") PasswordResetForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/password_reset_form";
        }

        User user = userService.findByUsername(form.getUsername());

        if (user == null) {
            bindingResult.rejectValue("username", "notFindUser", "해당 아이디의 유저는 존재하지 않습니다.");
            return "/password_reset_form";
        }

        // 비밀번호 초기화하기
        userService.resetPassword(user);
        
        return "redirect:/user/login";
    }
}
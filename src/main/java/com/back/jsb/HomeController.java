package com.back.jsb;

import com.back.jsb.domain.user.User;
import com.back.jsb.domain.user.UserService;
import com.back.jsb.global.security.UserSecurity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserSecurity userSecurity) throws IOException {
        User user = userSecurity.getUser();

        ProfileForm form = new ProfileForm(user);

        //이미지 처리
        byte[] imageBytes;
        if(user.getProfileImage() != null) {
            imageBytes = user.getProfileImage();
        } else {
            ClassPathResource imgFile = new ClassPathResource("static/images/defaultProfile.png");
            imageBytes = imgFile.getInputStream().readAllBytes();
        }
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        model.addAttribute("profileForm", form);
        model.addAttribute("profileImageOriginal", base64Image);
        model.addAttribute("originalNickname", user.getNickname());
        model.addAttribute("profileImageCurrent", null);

        return "profile";
    }

    @PostMapping("/profile")
    public String modify(
            Model model,
            @Valid @ModelAttribute ProfileForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserSecurity userSecurity
    ) throws IOException {
        User user = userSecurity.getUser();
        model.addAttribute("originalNickname", user.getNickname());

        // 기존 DB 이미지
        byte[] originalImageBytes = user.getProfileImage() != null ?
                user.getProfileImage() :
                new ClassPathResource("static/images/defaultProfile.png").getInputStream().readAllBytes();
        String originalBase64 = Base64.getEncoder().encodeToString(originalImageBytes);

        // 수정 중 이미지
        String currentBase64;
        if(form.profileImage() != null && !form.profileImage().isEmpty()){
            currentBase64 = Base64.getEncoder().encodeToString(form.profileImage().getBytes());
        } else {
            currentBase64 = originalBase64;
        }

        model.addAttribute("profileImageOriginal", originalBase64);
        model.addAttribute("profileImageCurrent", currentBase64);
        // 공백 확인
        if (bindingResult.hasErrors()) {
            return "/profile";
        }

        userService.modify(user, form);
        redirectAttributes.addFlashAttribute("msg", "회원 정보가 변경되었습니다.");
        return "redirect:/profile";
    }
}

package com.back.jsb;

import com.back.jsb.domain.user.User;
import com.back.jsb.global.security.UserSecurity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Base64;

@Controller
public class HomeController {


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserSecurity userSecurity) throws IOException {
        User user = userSecurity.getUser();

        //이미지 처리
        byte[] imageBytes;
        if(user.getProfileImage() != null) {
            imageBytes = user.getProfileImage();
        } else {
            ClassPathResource imgFile = new ClassPathResource("static/images/defaultProfile.png");
            imageBytes = imgFile.getInputStream().readAllBytes();
        }
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        model.addAttribute("user", user);
        model.addAttribute("profileImage", base64Image);
        return "profile";
    }
}

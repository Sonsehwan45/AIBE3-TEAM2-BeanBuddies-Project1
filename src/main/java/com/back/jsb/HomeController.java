package com.back.jsb;

import com.back.jsb.global.security.UserSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        String nickname = userSecurity.getUser().getNickname();
        model.addAttribute("nickname", nickname);

        return "home";
    }
}

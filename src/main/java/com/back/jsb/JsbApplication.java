package com.back.jsb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JsbApplication {
    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }
}




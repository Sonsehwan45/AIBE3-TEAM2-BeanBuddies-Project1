package com.back.jsb.domain.question;

import com.back.jsb.global.security.UserSecurity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/write")
    public String showWrite(@ModelAttribute("form") QuestionForm form) {
        return "question_form";
    }

    @PostMapping("/write")
    public String doWrite(
            @Valid @ModelAttribute("form") QuestionForm form,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserSecurity userSecurity
    ) {

        if(bindingResult.hasErrors()) {
            return "question_form";
        }

        questionService.register(form, userSecurity.getUser());

        return "redirect:/home";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
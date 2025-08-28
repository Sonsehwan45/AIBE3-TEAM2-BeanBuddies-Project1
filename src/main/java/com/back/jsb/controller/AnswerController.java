package com.back.jsb.controller;

import com.back.jsb.dto.AnswerForm;
import com.back.jsb.entity.Question;
import com.back.jsb.service.AnswerService;
import com.back.jsb.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/answers/write/{id}")
    public String write(@PathVariable Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Model model) {
        Question question = questionService.findById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            model.addAttribute("answerForm", answerForm);
            return "question/detail";
        }

        answerService.write(id, answerForm);

        return "redirect:/questions/detail/" + id;
    }
}

package com.back.domain.controller;

import com.back.domain.dto.AnswerForm;
import com.back.domain.entity.Answer;
import com.back.domain.entity.Question;
import com.back.domain.service.AnswerService;
import com.back.domain.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/form/{questionId}")
    public String createAnswer(
            @PathVariable Integer questionId,
            @Valid AnswerForm answerForm,
            BindingResult bindingResult,
            Model model
    ) {
        Question question = questionService.findById(questionId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "post/question/detail";
        }
        answerService.write(question, answerForm.getContent());
        return "redirect:/question/detail/" + questionId;
    }

    @GetMapping("/modify/{id}")
    public String showModify(
            @PathVariable("id") Integer id,
            AnswerForm answerForm,
            Model model
    ) {
        Answer answer = answerService.findById(id);

        answerForm.setContent(answer.getContent());

        model.addAttribute("answer", answer);

        return "post/answer/modify";
    }

    @PostMapping("/modify/{id}")
    public String modify(
            @PathVariable("id") Integer id,
            @Valid AnswerForm answerForm,
            BindingResult bindingResult,
            Model model
    ) {
        Answer answer = answerService.findById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("answer", answer);
            return "post/answer/modify";
        }
        answerService.modify(answer, answerForm.getContent());
        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Answer answer = answerService.findById(id);
        answerService.delete(answer);
        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }
}
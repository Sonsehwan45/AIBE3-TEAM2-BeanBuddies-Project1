package com.back.domain.controller;

import com.back.domain.dto.AnswerForm;
import com.back.domain.entity.Answer;
import com.back.domain.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/answer")
@Controller
public class AnswerController {
    @Autowired
    private AnswerService answerService;


    @PostMapping("/write")
    public String write(@Valid AnswerForm answerForm,
                        BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "question/question_detail" + answerForm.getQuestionId();
            }

            Answer answer = answerService.save(answerForm.getId(), answerForm.getContent(), answerForm.getQuestionId());

            return "redirect:/question/detail/" + answerForm.getQuestionId();
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable int id, Model model) {
        Answer answer = answerService.findById(id);

        AnswerForm answerForm = new AnswerForm(answer.getId(), answer.getContent(), answer.getQuestion().getId());

        model.addAttribute("answerForm", answerForm);

        return "answer/answer_form";
    }

    @PostMapping("/modify/{id}")
    public String modify(@Valid AnswerForm answerForm,
                         @PathVariable int id,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "answer/answer_form";
        }

        answerService.save(id, answerForm.getContent(), answerForm.getQuestionId());

        return "redirect:/question/detail/" + answerForm.getQuestionId();
    }
}

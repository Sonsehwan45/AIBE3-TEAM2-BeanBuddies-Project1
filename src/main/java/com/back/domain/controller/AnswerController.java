package com.back.domain.controller;

import com.back.domain.dto.AnswerForm;
import com.back.domain.entity.Answer;
import com.back.domain.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/answer")
@Controller
public class AnswerController {
    @Autowired
    private AnswerService answerService;


    @PostMapping("/write/{questionId}")
    public String write(@Valid AnswerForm answerForm,
                        @PathVariable("questionId") int questionId,
                        BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "question/question_detail" + questionId;
            }

            Answer answer = answerService.save(answerForm.getId(), answerForm.getContent(), questionId);

            return "redirect:/question/detail/" + questionId;
    }
}

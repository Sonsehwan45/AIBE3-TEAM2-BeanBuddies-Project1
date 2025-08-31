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
import org.springframework.web.bind.annotation.*;

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;


    @PostMapping("/write")
    public String write(@Valid AnswerForm answerForm,
                        BindingResult bindingResult,
                        Model model) {
            if (bindingResult.hasErrors()) {
                Question question = questionService.findById(answerForm.getQuestionId());
                model.addAttribute("question", question);
                model.addAttribute("answerForm", answerForm);
                return "question/question_detail";
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
    public String modify(@PathVariable int id,
                         @Valid AnswerForm answerForm,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "answer/answer_form";
        }

        answerService.save(id, answerForm.getContent(), answerForm.getQuestionId());

        return "redirect:/question/detail/" + answerForm.getQuestionId();
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        Answer answer = answerService.findById(id);

        int questionId = answer.getQuestion().getId();

        answerService.delete(answer);

        return "redirect:/question/detail/" + questionId;
    }
}

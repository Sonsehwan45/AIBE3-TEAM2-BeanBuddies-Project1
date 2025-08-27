package com.back.domain.controller;

import com.back.domain.dto.AnswerForm;
import com.back.domain.dto.QuestionForm;
import com.back.domain.entity.Question;
import com.back.domain.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);
        return "question/question_list";
    }

    @GetMapping("/write")
    public String write(QuestionForm questionForm) {
        return "question/question_form";
    }

    @PostMapping("/write")
    public String write(
            @Valid QuestionForm questionForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "question/question_form";
        }

        Question question = questionService.save(null, questionForm.getTitle(), questionForm.getContent());

        model.addAttribute("question", question);

        return "redirect:/question/detail/" + question.getId();
    }

    @GetMapping("/detail/{id}")
    @Transactional(readOnly = true)
    public String detail(@PathVariable int id, AnswerForm answerForm, Model model) {
        Question question = questionService.findById(id);

        model.addAttribute("question", question);
        answerForm.setQuestionId(question.getId());
        model.addAttribute("answerForm", answerForm);

        return "question/question_detail";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable int id, Model model) {
        Question question = questionService.findById(id);

        QuestionForm questionForm = new QuestionForm(question.getId(), question.getTitle(), question.getContent());

        model.addAttribute("questionForm", questionForm);
        return "question/question_form";
    }

    @PostMapping("/modify/{id}")
    public String modify(
            @PathVariable Integer id,
            @Valid QuestionForm questionForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "question/question_form";
        }

        questionService.save(id, questionForm.getTitle(), questionForm.getContent());

        return "redirect:/question/detail/" + id;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        Question question = questionService.findById(id);

        questionService.delete(question);

        return "redirect:/";
    }
}
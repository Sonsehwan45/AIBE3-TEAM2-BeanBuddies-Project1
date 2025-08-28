package com.back.jsb.controller;

import com.back.jsb.dto.AnswerForm;
import com.back.jsb.dto.QuestionForm;
import com.back.jsb.entity.Question;
import com.back.jsb.service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);

        return "question/list";
    }

    @GetMapping("/write")
    public String write(QuestionForm questionForm) {
        return "question/write";
    }

    @PostMapping("/write")
    public String doWrite(@Valid QuestionForm questionForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("questionForm", questionForm);
            return "question/write";
        }

        Question question = questionService.write(questionForm.title(), questionForm.content());

        return "redirect:/questions/detail/" + question.getId();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model, AnswerForm answerForm) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);

        return "question/detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        try {
            questionService.delete(id);
        } catch (EntityNotFoundException e) {
            //TODO : 존재하지 않는 게시글입니다. 팝업 or 안내페이지
        }

        return "redirect:/questions/list";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);

        return "/question/update";
    }

    @PostMapping("/update/{id}")
    public String doUpdate(@PathVariable Integer id, @Valid QuestionForm questionForm, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("questionForm", questionForm);
            return "question/update";
        }

        try {
            questionService.update(id, questionForm.title(), questionForm.content());
        } catch (EntityNotFoundException e) {
            //TODO : 존재하지 않는 게시글입니다. 팝업 or 안내페이지
        }

        return "redirect:/questions/list";
    }

}
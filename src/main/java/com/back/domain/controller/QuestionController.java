package com.back.domain.controller;

import com.back.domain.dto.AnswerForm;
import com.back.domain.dto.QuestionForm;
import com.back.domain.entity.Question;
import com.back.domain.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
    @Autowired
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "kw", defaultValue = "") String kw) {
        List<Question> questions = questionService.search(kw);
        model.addAttribute("questions", questions);
        return "post/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/form")
    public String write(@ModelAttribute("form") QuestionForm form) {
        return "post/question/form";
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @PostMapping("/form")
    public String write(
            @ModelAttribute("form") @Valid QuestionForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "post/question/form";
        }

        Question question = questionService.write(form.getTitle(), form.getContent());
        model.addAttribute("question", question);
        return "redirect:/question/detail/" + question.getId();
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id, AnswerForm answerForm) {
        model.addAttribute("question", questionService.findById(id));
        return "post/question/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Integer id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);

        return "post/question/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(
            @PathVariable("id") Integer id,
            @ModelAttribute("form") @Valid QuestionForm form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "post/question/modify";
        }
        Question question = questionService.findById(id);

        questionService.modify(question, form.getTitle(), form.getContent());

        return "redirect:/question/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        questionService.delete(id);

        return "redirect:/question/list";
    }


}
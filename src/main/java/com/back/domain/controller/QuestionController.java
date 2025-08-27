package com.back.domain.controller;

import com.back.domain.dto.QuestionForm;
import com.back.domain.entity.Question;
import com.back.domain.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class QuestionController {
    @Autowired
    private final QuestionService questionService;

    @Transactional(readOnly = true)
    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);

        return "post/list";
    }

    @GetMapping("/form")
    public String write(@ModelAttribute("form") QuestionForm form) {
        return "post/form";
    }

    @Transactional
    @PostMapping("/form")
    public String write(
            @ModelAttribute("form") @Valid QuestionForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "post/form";
        }

        Question question = questionService.write(form.getTitle(), form.getContent());
        model.addAttribute("question", question);
        return "redirect:/post/detail/" + question.getId();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);

        return "post/detail";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Integer id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);

        return "post/modify";
    }

    @PostMapping("/modify/{id}")
    public String modify(
            @PathVariable("id") Integer id,
            @ModelAttribute("form") @Valid QuestionForm form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "post/modify";
        }
        Question question = questionService.findById(id);

        // 서비스 계층의 modify 메서드를 호출하여 데이터를 수정합니다.
        questionService.modify(question, form.getTitle(), form.getContent());

        return "redirect:/post/detail/" + id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        questionService.delete(id);

        return "redirect:/post/list";
    }
}
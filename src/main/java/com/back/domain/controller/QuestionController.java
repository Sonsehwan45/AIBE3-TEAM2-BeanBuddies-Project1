package com.back.domain.controller;

import com.back.domain.dto.AnswerForm;
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

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
    @Autowired
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "kw", defaultValue = "") String kw) {
        // 서비스의 검색 메서드를 호출합니다.
        List<Question> questions = questionService.search(kw);
        model.addAttribute("questions", questions);
        return "post/question/list";
    }

    @GetMapping("/form")
    public String write(@ModelAttribute("form") QuestionForm form) {
        return "post/question/form";
    }

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

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Integer id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);

        return "post/question/modify";
    }

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

        // 서비스 계층의 modify 메서드를 호출하여 데이터를 수정합니다.
        questionService.modify(question, form.getTitle(), form.getContent());

        return "redirect:/question/detail/" + id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        questionService.delete(id);

        return "redirect:/question/list";
    }


}
package com.back.jsb.controller;

import com.back.jsb.dto.AnswerForm;
import com.back.jsb.dto.QuestionForm;
import com.back.jsb.entity.Question;
import com.back.jsb.entity.SiteUser;
import com.back.jsb.service.QuestionService;
import com.back.jsb.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "keyword", defaultValue = "") String keyword, Model model) {
        List<Question> questions = questionService.findAll(keyword);
        model.addAttribute("questions", questions);

        return "question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String write(QuestionForm questionForm) {
        return "question/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String doWrite(@Valid QuestionForm questionForm, BindingResult bindingResult,
                          Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("questionForm", questionForm);
            return "question/write";
        }

        SiteUser siteUser = userService.getSiteUser(principal.getName());
        Question question = questionService.write(questionForm.title(), questionForm.content(), siteUser);

        return "redirect:/questions/detail/" + question.getId();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model, AnswerForm answerForm) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);

        return "question/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        try {
            questionService.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다.");
        }
        return "redirect:/questions/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id, Model model) {
        Question question = questionService.findById(id);
        QuestionForm questionForm = new QuestionForm(question.getTitle(), question.getContent());
        model.addAttribute("questionForm", questionForm);

        return "/question/update";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String doUpdate(@PathVariable Integer id, @Valid QuestionForm questionForm, Principal principal,
                           Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("questionForm", questionForm);
            return "question/update";
        }

        Question question = questionService.findById(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        try {
            questionService.update(question, questionForm.title(), questionForm.content());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다.");
        }

        return "redirect:/questions/detail/" + id;
    }
}
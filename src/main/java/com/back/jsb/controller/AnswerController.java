package com.back.jsb.controller;

import com.back.jsb.dto.AnswerForm;
import com.back.jsb.entity.Answer;
import com.back.jsb.entity.Question;
import com.back.jsb.entity.SiteUser;
import com.back.jsb.service.AnswerService;
import com.back.jsb.service.QuestionService;
import com.back.jsb.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.security.Principal;
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
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write/{id}")
    public String write(@PathVariable Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult,
                        Principal principal, Model model) {
        Question question = questionService.findById(id);
        SiteUser siteUser = userService.getSiteUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            model.addAttribute("answerForm", answerForm);
            return "question/detail";
        }

        answerService.write(id, answerForm, siteUser);

        return "redirect:/questions/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id, Model model) {
        Answer answer = answerService.findById(id);
        model.addAttribute("answerForm", new AnswerForm(answer.getContent()));

        return "answer/update";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String answerModify(@PathVariable("id") Integer id, @Valid AnswerForm answerForm,
                               BindingResult bindingResult,
                               Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("answerForm", answerForm);
            return "answer/update";
        }

        Answer answer = answerService.findById(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        answerService.update(answer, answerForm.content());

        return String.format("redirect:/questions/detail/%d", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Principal principal) {
        Answer answer = answerService.findById(id);

        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        try {
            answerService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "답변이 존재하지 않습니다");
        }

        return String.format("redirect:/questions/detail/%d", answer.getQuestion().getId());
    }
}

package com.back.jsb.domain.question;

import com.back.jsb.global.security.UserSecurity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/write")
    public String showWrite(@ModelAttribute("form") QuestionForm form) {
        return "question_form";
    }

    @PostMapping("/write")
    public String doWrite(
            @Valid @ModelAttribute("form") QuestionForm form,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserSecurity userSecurity
    ) {

        if(bindingResult.hasErrors()) {
            return "question_form";
        }

        questionService.register(form, userSecurity.getUser());

        return "redirect:/home";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal) {

        Question question = questionService.findById(id);
        if(question == null) {
            System.out.println("해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        if(principal == null || !question.getAuthor().getUsername().equals(principal.getName())) {
            System.out.println("해당 질문글을 삭제할 권한이 없습니다. 본인만 삭제할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        questionService.deleteById(id);
        return "redirect:/question/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Long id, Principal principal, Model model) {

        Question question = questionService.findById(id);
        if(question == null) {
            System.out.println("해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        if(principal == null || !question.getAuthor().getUsername().equals(principal.getName())) {
            System.out.println("해당 질문글을 수정할 권한이 없습니다. 본인만 수정할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        model.addAttribute("form", new QuestionForm(question));

        return "question_form";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id,
                         @ModelAttribute("form") QuestionForm form,
                         Principal principal
    ) {

        Question question = questionService.findById(id);
        if(question == null) {
            System.out.println("해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        if(principal == null || !question.getAuthor().getUsername().equals(principal.getName())) {
            System.out.println("해당 질문글을 수정할 권한이 없습니다. 본인만 수정할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        questionService.modify(question, form);

        return "redirect:/question/detail/%d".formatted(id);
    }
}
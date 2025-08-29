package com.back.jsb.domain.question;

import com.back.jsb.domain.answer.AnswerForm;
import com.back.jsb.global.security.UserSecurity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("form", new AnswerForm());
        return "question_detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         Principal principal,
                         RedirectAttributes redirectAttributes
    ) {

        Question question = questionService.findById(id);
        if(question == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        if(principal == null || !question.getAuthor().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("msg", "삭제 권한이 없습니다. 본인만 삭제할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        questionService.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "질문이 삭제되었습니다.");
        return "redirect:/question/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Long id,
                         Principal principal,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        Question question = questionService.findById(id);
        if(question == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        if(principal == null || !question.getAuthor().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("msg", "수정 권한이 없습니다. 본인만 수정할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        model.addAttribute("form", new QuestionForm(question));
        return "question_form";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id,
                         @Valid @ModelAttribute("form") QuestionForm form,
                         BindingResult bindingResult,
                         Principal principal,
                         RedirectAttributes redirectAttributes
    ) {

        Question question = questionService.findById(id);
        if(question == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        if(principal == null || !question.getAuthor().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("msg", "수정 권한이 없습니다. 본인만 수정할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        if(bindingResult.hasErrors()) {
            return "question_form";
        }

        questionService.modify(question, form);
        redirectAttributes.addFlashAttribute("msg", "질문이 수정되었습니다.");
        return "redirect:/question/detail/%d".formatted(id);
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(value="type", required=false) List<String> types,
            @RequestParam(value="keyword", required=false) String keyword,
            Model model
    ) {

        if(keyword == null) {
            return "redirect:/question/list";
        }

        if(types == null || types.isEmpty() || types.contains("all")) {
            types = List.of("title", "content", "answer", "author");
        }

        List<Question> questions = questionService.search(types, keyword);
        model.addAttribute("questions", questions);
        return "question_list";
    }
}
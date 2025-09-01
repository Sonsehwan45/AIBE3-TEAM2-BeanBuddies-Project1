package com.back.jsb.domain.question;

import com.back.jsb.domain.answer.AnswerForm;
import com.back.jsb.global.security.UserSecurity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    public String write(@ModelAttribute("form") QuestionForm form) {
        return "question_form";
    }

    @PostMapping("/write")
    @Transactional
    public String write(
            @Valid @ModelAttribute("form") QuestionForm form,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserSecurity userSecurity
    ) {
        //공백인 필드가 있을 때 예외처리
        if(bindingResult.hasErrors()) {
            return "question_form";
        }

        questionService.register(form, userSecurity.getUser());

        return "redirect:/home";
    }

    @GetMapping("/list")
    public String list(
            @RequestParam(value="type", required=false) List<String> types,
            @RequestParam(value="keyword", required=false) String keyword,
            Model model
    ) {
        //검색 버튼 누른 후에도, 검색 설정이 지워지지 않도록 저장
        model.addAttribute("selectedTypes", types != null ? types : List.of("all"));
        model.addAttribute("keyword", keyword);

        List<Question> questions;
        //검색어 유무에 따라 findAll or Search
        if (keyword == null || keyword.trim().isEmpty()) {
            questions = questionService.findAll();
        } else {
            questions = questionService.search(types, keyword);
        }

        model.addAttribute("questions", questions);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        //해당 질문글 찾기
        Question question = questionService.findById(id);

        //질문글이 존재하지 않을 때의 예외처리
        if(question == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        model.addAttribute("question", question);
        model.addAttribute("form", new AnswerForm());
        return "question_detail";
    }

    @PostMapping("/delete/{id}")
    @Transactional
    public String delete(
            @PathVariable Long id,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        //해당 질문글 찾기
        Question question = questionService.findById(id);

        //질문글이 존재하지 않을 때의 예외처리
        if(question == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        //로그인하지 않았거나, 본인이 아닐 때의 예외처리
        if(principal == null || !question.getAuthor().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("msg", "삭제 권한이 없습니다. 본인만 삭제할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        questionService.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "질문이 삭제되었습니다.");
        return "redirect:/question/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(
            @PathVariable Long id,
            Principal principal,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        //해당 질문글 찾기
        Question question = questionService.findById(id);

        //질문글이 존재하지 않을 때의 예외처리
        if(question == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        //로그인하지 않았거나, 본인이 아닐 때의 예외처리
        if(principal == null || !question.getAuthor().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("msg", "수정 권한이 없습니다. 본인만 수정할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        model.addAttribute("form", new QuestionForm(question));
        return "question_form";
    }

    @PostMapping("/modify/{id}")
    @Transactional
    public String modify(
            @PathVariable Long id,
            @Valid @ModelAttribute("form") QuestionForm form,
            BindingResult bindingResult,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        //해당 질문글 찾기
        Question question = questionService.findById(id);

        //질문글이 존재하지 않을 때의 예외처리
        if(question == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        //로그인하지 않았거나, 본인이 아닐 때의 예외처리
        if(principal == null || !question.getAuthor().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("msg", "수정 권한이 없습니다. 본인만 수정할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        //공백인 필드가 있을 때 예외처리
        if(bindingResult.hasErrors()) {
            return "question_form";
        }

        questionService.modify(question, form);
        redirectAttributes.addFlashAttribute("msg", "질문이 수정되었습니다.");
        return "redirect:/question/detail/%d".formatted(id);
    }
}
package com.back.jsb.domain.answer;

import com.back.jsb.domain.question.Question;
import com.back.jsb.domain.question.QuestionService;
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

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    private static final int DEFAULT_PAGE_SIZE = 10;

    @PostMapping("/write/{id}")
    @Transactional
    public String write(
            @PathVariable Long id,
            @Valid @ModelAttribute("form") AnswerForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserSecurity userSecurity
    ) {
        //해당 질문글 찾기
        Question question = questionService.findById(id);

        //질문글이 존재하지 않을 때 예외처리
        if (question == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/list";
        }

        //로그인하지 않았을 때 예외처리
        if (userSecurity == null) {
            redirectAttributes.addFlashAttribute("msg", "댓글 작성은 로그인 후 가능합니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        //내용이 공백일 때 예외처리
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        answerService.register(form, question, userSecurity.getUser());
        redirectAttributes.addFlashAttribute("msg", "댓글이 작성되었습니다.");
        return "redirect:/question/detail/%d".formatted(id);
    }

    @PostMapping("/delete/{question_id}/{answer_id}")
    @Transactional
    public String delete(
            @PathVariable("question_id") Long questionId,
            @PathVariable("answer_id") Long answerId,
            RedirectAttributes redirectAttributes,
            Principal principal
    ) {
        //해당 답변 찾기
        Answer answer = answerService.findById(answerId);

        //답변이 존재하지 않을 때 예외처리
        if (answer == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 답변이 존재하지 않습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        //로그인하지 않았거나 본인이 아닐 때 예외처리
        if (principal == null || !answer.getAuthor().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("msg", "댓글 삭제 권한이 없습니다. 본인만 삭제할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        answerService.deleteById(answerId);
        redirectAttributes.addFlashAttribute("msg", "댓글이 삭제되었습니다.");
        return "redirect:/question/detail/%d".formatted(questionId);
    }

    @GetMapping("/modify/{question_id}/{answer_id}")
    public String modify(
            @PathVariable("question_id") Long questionId,
            @PathVariable("answer_id") Long answerId,
            @ModelAttribute("editForm") AnswerForm form,
            Model model,
            RedirectAttributes redirectAttributes,
            Principal principal
    ) {
        //해당 답변 찾기
        Answer answer = answerService.findById(answerId);

        //답변이 존재하지 않을 때 예외처리
        if (answer == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 답변이 존재하지 않습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        //로그인하지 않았거나 본인이 아닐 때 예외처리
        if (principal == null || !answer.getAuthor().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("msg", "댓글 수정 권한이 없습니다. 본인만 수정할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        model.addAttribute("form", new AnswerForm());
        model.addAttribute("question", questionService.findById(questionId));
        model.addAttribute("editForm", new AnswerForm(answer));
        model.addAttribute("editAnswerId", answerId);
        return "question_detail";
    }

    @PostMapping("/modify/{question_id}/{answer_id}")
    @Transactional
    public String modify(
            @PathVariable("question_id") Long questionId,
            @PathVariable("answer_id") Long answerId,
            @Valid @ModelAttribute("editForm") AnswerForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            Principal principal
    ) {
        //해당 답변 찾기
        Answer answer = answerService.findById(answerId);

        //답변이 존재하지 않을 때 예외처리
        if (answer == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 답변이 존재하지 않습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        //로그인하지 않았거나 본인이 아닐 때 예외처리
        if (principal == null || !answer.getAuthor().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("msg", "댓글 수정 권한이 없습니다. 본인만 수정할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        //공백인 필드가 있을 때 예외처리
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", questionService.findById(questionId));
            model.addAttribute("editAnswerId", answerId);
            model.addAttribute("form", new AnswerForm());
            return "question_detail";
        }

        answerService.modify(answer, form);
        redirectAttributes.addFlashAttribute("msg", "댓글이 수정되었습니다.");
        return "redirect:/question/detail/%d".formatted(questionId);
    }

    @GetMapping("list")
    public String list(@RequestParam(defaultValue = "0") Integer page) {
        answerService.findRecentAnswers(page, DEFAULT_PAGE_SIZE);
        return "answer_list";
    }
}

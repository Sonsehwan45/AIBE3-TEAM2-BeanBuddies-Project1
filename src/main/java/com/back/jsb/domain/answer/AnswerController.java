package com.back.jsb.domain.answer;

import com.back.jsb.domain.question.Question;
import com.back.jsb.domain.question.QuestionService;
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

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/write/{id}")
    public String write(
            @PathVariable Long id,
            @Valid @ModelAttribute("form") AnswerForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserSecurity userSecurity
    ) {
        Question question = questionService.findById(id);
        if(question == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 질문글이 존재하지 않습니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        if (userSecurity == null) {
            redirectAttributes.addFlashAttribute("msg", "댓글 작성은 로그인 후 가능합니다.");
            return "redirect:/question/detail/%d".formatted(id);
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        answerService.register(form, question, userSecurity.getUser());
        redirectAttributes.addFlashAttribute("msg", "댓글이 작성되었습니다.");
        return "redirect:/question/detail/%d".formatted(id);
    }

    @PostMapping("/delete/{question_id}/{answer_id}")
    public String delete(
            @PathVariable("question_id") Long questionId,
            @PathVariable("answer_id") Long answerId,
            RedirectAttributes redirectAttributes,
            Principal principal
    ) {
        Answer answer = answerService.findById(answerId);
        if(answer == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 답변이 존재하지 않습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

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
        Answer answer = answerService.findById(answerId);
        if(answer == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 답변이 존재하지 않습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

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
    public String modify(
            @PathVariable("question_id") Long questionId,
            @PathVariable("answer_id") Long answerId,
            @Valid @ModelAttribute("editForm") AnswerForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            Principal principal
    ) {
        Answer answer = answerService.findById(answerId);
        if(answer == null) {
            redirectAttributes.addFlashAttribute("msg", "해당 답변이 존재하지 않습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        if(principal == null || !answer.getAuthor().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("msg", "댓글 수정 권한이 없습니다. 본인만 수정할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("question", questionService.findById(questionId));
            model.addAttribute("editAnswerId", answerId);
            model.addAttribute("form", new AnswerForm());
            return "question_detail";
        }

        answerService.modify(answer, form);
        redirectAttributes.addFlashAttribute("msg", "댓글이 수정되었습니다.");
        return "redirect:/question/detail/%d".formatted(questionId);
    }


}

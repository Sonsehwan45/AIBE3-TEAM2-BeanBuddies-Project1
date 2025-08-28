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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            Model model,
            BindingResult bindingResult,
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
            bindingResult.rejectValue("content", "내용을 작성해주세요");
            model.addAttribute("question", question);
            return "question_detail";
        }

        answerService.register(form, question, userSecurity.getUser());
        redirectAttributes.addFlashAttribute("msg", "댓글이 작성되었습니다.");
        return "redirect:/question/detail/%d".formatted(id);
    }
}
